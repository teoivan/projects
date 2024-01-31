const express = require('express');
const bodyParser=require("body-parser");
const mysql = require('mysql2');
const cors = require('cors');
const app = express();
const port = 3001;

app.use(cors());


const db = mysql.createConnection({
  host: 'localhost',
  port: '3306',
  user: 'root',
  password: 'admin',
  database: 'supermarket_store_db',
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

db.connect((err) => {
  if (err) {
    console.error('MySQL connection error:', err);
  } else {
    console.log('Connected to MySQL database');
  }
});


app.get('/api/search', async (req, res) => {
  const { query } = req.query;

  try {
    const searchResult = await performSearch(query);
    console.log('I get the result:', searchResult);
    res.json(searchResult);
  } catch (error) {
    console.error('Error performing search:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});



app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});

function performSearch(query) {
  return new Promise((resolve, reject) => {
    try {
      console.log('Performing search for:', query);
      db.query(`SELECT * FROM products WHERE name LIKE '${query}%'`, (error, result) => {
        if (error) {
          console.error('Error performing search in the database:', error.message);
          reject(error.message);
        }
  
        if (result && result.length > 0) {
          console.log('Search result:', result);
          resolve(result);
        } else {
          console.log('Searched no result');
          resolve(null);
        }
      });
    } catch (error) {
      console.error('Error performing search in the database:', error.message);
      reject(error.message);
    }
  })
}

app.get(`/api/products`, (req, res) => {
  const query = `SELECT * FROM products`;
  db.query(query, (err, data) => {
    if (err) {
      return res.json(err);
    } else {
      return res.json(data);
    }
  });
});

app.post('/api/signup', (req, res) => {
  const { username, email, password } = req.body;

  const checkUserQuery = `SELECT * FROM users WHERE username = ? OR email = ?`;
  db.query(checkUserQuery, [username, email], (checkError, checkResults) => {
   
    if (checkResults.length > 0) {
      res.status(409).json({ success: false, message: 'Username or email already exists' });
      return;
    } 
    if (checkError) {
      console.error('Error executing user check query:', checkError);
      res.status(500).json({ success: false, message: 'Internal Server Error' });
      return;
    }

    const insertUserQuery = `INSERT INTO users (username, email, password) VALUES (?, ?, ?)`;
    db.query(insertUserQuery, [username, email, password], (insertError, insertResults) => {
      if (insertError) {
        console.error('Error executing signup query:', insertError);
        res.status(500).json({ success: false, message: 'Internal Server Error' });
        return;
      }
      const userId = insertResults.insertId;
      res.json({ success: true, message: 'Sign up successfully', user_id: userId});

    });
  });
});



app.post('/api/signin', (req, res) => {
  const { email, password } = req.body;

  const query = `SELECT * FROM users WHERE email = ? AND password = ?`;
  db.query(query, [email, password], (error, results) => {
    if (error) {
      console.error('Error executing signin query:', error);
      res.status(500).json({ success: false, message: 'Internal Server Error' });
      return;
    }

    if (results.length === 0) {
      res.status(401).json({ success: false, message: 'Invalid email or password' });
    } else {
      const username = results[0].username; 
      const id=results[0].id;
      res.json({
        success: true,
        message: 'Sign in successfully',
        username: username,
        user_id: id, 
      });
    }
  });
});

app.post('/api/shopping-list', (req, res) => {
  const { userId, listName, items } = req.body;
  db.query(
    'INSERT INTO shopping_lists (user_id, list_name) VALUES (?, ?)',
    [userId, listName],
    (err, result) => {
      if (err) {
        console.error('Error creating shopping list:', err);
        res.status(500).json({ error: 'Internal Server Error' });
      } else {
        const listId = result.insertId;
        items.forEach((item) => {
          db.query(
            'INSERT INTO list_items (list_id, product_id, product_name, quantity) VALUES (?, ?, ?, ?)',
            [listId, item.prod_id, item.name, item.quantity],
            (err) => {
              if (err) {
                console.error('Error adding item to shopping list:', err);
              }
            }
          );
        });
        res.status(201).json({ message: 'Shopping list created successfully' });
      }
    }
  );
});

app.get('/api/shopping-lists/:userId', (req, res) => {
  const userId = req.params.userId;
  const query = `SELECT * FROM shopping_lists WHERE user_id = ? ORDER BY created_at DESC`;

  db.query(query, [userId], (err, data) => {
    if (err) {
      console.error('Error fetching shopping lists:', err);
      res.status(500).json({ error: 'Internal Server Error' });
    } else {
      res.json(data);
    }
  });
});

app.get('/api/list/:listId/products', (req, res) => {
  const listId = req.params.listId;
  const query = `
    SELECT li.*, p.*
    FROM list_items li
    JOIN products p ON li.product_id = p.prod_id
    WHERE li.list_id = ?;
  `;

  db.query(query, [listId], (err, data) => {
    if (err) {
      console.error('Error fetching products for the shopping list:', err);
      res.status(500).json({ error: 'Internal Server Error' });
    } else {
      console.log(data);
      res.json(data);
    }
  });
});