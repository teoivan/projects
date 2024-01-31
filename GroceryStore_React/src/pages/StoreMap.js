import React, { useState, useContext } from "react";
import "./StoreMap.css";
import NonGeographicMap from "../components/Map";
import { ShoppingListContext } from "../App";

const StoreMap = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResult, setSearchResult] = useState([]);
  const [searched, setSearched] = useState(false);
  const { shoppingList, setShoppingList } = useContext(ShoppingListContext);

  const handleSearch = async () => {
    try {
      if (searchTerm.trim() === '') {
        return;
      }

      const response = await fetch(`http://localhost:3001/api/search?query=${searchTerm}`);
      const result = await response.json();

      setSearchResult(result);
      setSearched(true);
    } catch (error) {
      console.error('Error searching for the product:', error.message);
    }
  };

  const addToShoppingList = (product) => {
    const existingProduct = shoppingList.find(productInList => productInList.prod_id === product.prod_id);
    if (existingProduct) {
      if (!existingProduct.quantity) existingProduct.quantity = 0;
      existingProduct.quantity++;
    } else {
      product.quantity = 1;
      setShoppingList([...shoppingList, product]); 
    }
  };

  return (
    <div className="store-map-container">
      <h1>Product Search</h1>
      <div className="search-container">
        <input
          type="text"
          placeholder="Enter product name"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
      </div>

      {searched && searchResult && searchResult.length > 0 ? (
        <div className="result-container">
          <h2>Search Result(s)</h2>
          {searchResult.map((result, index) => (
            <div key={index} className="product-details">
              <p>{result.name}</p>
              {result.img && (
                <img
                  src={result.img}
                  alt={result.name}
                  className="product-image"
                />
              )}
              <p>{result.description}</p>
              <p>Price: {result.price ? result.price+' $': 'Not available'}</p>
              <p>Stock Status: {result ? (result.stock ? (result.stock <= 3 ? "There are less than 3 pieces left! HURRY UP!" : 'In Stock') : 'Out of Stock') : 'Not available'}</p>
              <button onClick={() => addToShoppingList(result)}> Add to List</button>
              <p>See on map</p>
              <NonGeographicMap
                locations={[
                  {
                    latitude: result.latitude,
                    longitude: result.longitude,
                    name: result.name,
                    img: result.img,
                }]}
              />
            </div>
          ))}
        </div>
      ) : searched ? (
        <p className="no-result">No product found for the given search term.</p>
      ) : null}
    </div>
  );
};

export default StoreMap;
