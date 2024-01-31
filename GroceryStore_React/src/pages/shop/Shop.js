import React, { useState, useEffect, useContext } from "react";
import "./Shop.css";
import { ShoppingListContext } from "../../App";
import Select from 'react-select';

export const Shop = () => {
  const [products, setProducts] = useState([]);
  const [sortBy, setSortBy] = useState('');
  const { shoppingList, setShoppingList } = useContext(ShoppingListContext);

  useEffect(() => {
   
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:3001/api/products`);
        const data = await response.json();
        console.log(data);
        if (sortBy === 'priceAsc') {
          setProducts(data.sort((a, b) => a.price - b.price));
        } else if (sortBy==='default') {
          setProducts(data);
        } else if(sortBy==='priceDesc'){
          setProducts(data.sort((a, b) => b.price - a.price));
        }else if(sortBy==='name'){
          setProducts(data.sort((a, b) => a.name.localeCompare(b.name)));
        }
        else{
          setProducts(data);
        }
        
      } catch (err) {
        console.error('Error fetching data:', err);
      }
    };

    fetchData();
  }, [sortBy]);

  const addToShoppingList = (product) => {
    const existingProduct = shoppingList.find(productInList => productInList.prod_id === product.prod_id);
    if (existingProduct) {
      if (!existingProduct.quantity) existingProduct.quantity = 0;
      existingProduct.quantity++;
    } else {
      product.quantity = 1;
      shoppingList.push(product);
    }
    setShoppingList(shoppingList);
  };

  const options = [
    { value: 'default', label: 'Default' },
    { value:'name', label:'Name'},
    { value: 'priceAsc', label: 'Price ascending' },
    { value: 'priceDesc', label: 'Price descending' },
  ];

    return (
      <div>
      <div className="product-container">
        <div className="shopTitle">
          <p>Products</p>
        </div>
          <div className="view-by">
        <label htmlFor="sortSelect">Sort by:</label>
        <Select
        id="yourSelect"
        className="customSelect"
        options={options}
        onChange={(selectedOption) => setSortBy(selectedOption.value)}
        defaultValue={options[0]}
        styles={{
          option: (provided, state) => ({
            ...provided,
            backgroundColor: state.isSelected? '#c8e6b1':'inherit',
            "&:hover": {
              backgroundColor:state.isSelected?'#c8e6b1':'#edf7e6',
            },
            color: 'black',
          }),
          control: (provided, state) => ({
            ...provided,
            width: 190,
            borderColor: state.isFocused ? '#2c5e2d' : '#2c5e2d',
            boxShadow: '0 0 3px #2c5e2d',
            border: '0 0 10px #2c5e2d',
            "&:hover": {
              border: '30px #2c5e2d'
            },
          }),
          menu: (provided) => ({
            ...provided,
            borderRadius: 8,
            borderColor: '0 0 10px #2c5e2d',
          }),
          menuList: (provided) => ({
            ...provided,
            padding: 10, 
            borderColor: '0 0 10px #2c5e2d',
          }),
          dropdownIndicator: base => ({
            ...base,
            color: "#2c5e2d" 
          })
        }}
      />
      </div>
      <div className="product-list">
        {products.map((product) => (
          <div key={product.prod_id} className="product-item">
            <img src={product.img} alt={product.name} />
            <div className="product-details">
              <strong>{product.name}</strong><br />
              Price: {product.price} USD<br />
              <p>{product.description} </p>
              <button onClick={() => addToShoppingList(product)}> Add to List</button>
            </div> 
           
          </div>
        ))}
      </div>
    </div>
    </div>
    );
  };
  

