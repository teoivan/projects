import React, { createContext, useState } from 'react';
import { BrowserRouter as  Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Home from './pages/Home';
import { Shop } from './pages/shop/Shop';
import StoreMap from './pages/StoreMap';
import List from "./pages/List";
import Account from './pages/Account';
import { AuthProvider } from './hooks/AuthContext';




export const ShoppingListContext = createContext([]);

function App() {
  const [shoppingList, setShoppingList] = useState([]);
  

  return (
    <Router>
       <AuthProvider>
      <div className="App">
        <Navbar />
        <ShoppingListContext.Provider value={{ shoppingList, setShoppingList }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/search" element={<StoreMap />} />
            <Route path="/products" element={<Shop />} />
            <Route path="/list" element={<List />}></Route>
            <Route path="/account" element={<Account />}></Route>
          </Routes>
        </ShoppingListContext.Provider>
        <Footer />
      </div>
      </AuthProvider>
    </Router>
  );
}

export default App;
