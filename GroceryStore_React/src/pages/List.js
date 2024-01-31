import React, { useContext, useState } from "react";
import { ShoppingListContext } from "../App";
import "./List.css";
import NonGeographicMap from "../components/Map";
import { useAuth } from "../hooks/AuthContext";

const List = () => {
  const { shoppingList, setShoppingList } = useContext(ShoppingListContext);
  const { user } = useAuth();
  const [saveConfirmationMessage, setSaveConfirmationMessage] = useState('');

  const [message, setMessage] = useState('');
  const [userMessage, setUserMessage] = useState('');
  const [showSavePopup, setShowSavePopup] = useState(false);
  const [listName, setListName] = useState('');

  const [showConfirmation, setShowConfirmation] = useState(false);

  const updateQuantity = (prodId, newQuantity) => {
    const updatedList = shoppingList.map(productInList => {
      if (productInList.prod_id === prodId) {
        return { ...productInList, quantity: Math.max(newQuantity, 1) };
      }
      return productInList;
    });
    setShoppingList(updatedList);

  };

  const removeFromList = (prodId) => {
    const updatedList = shoppingList.filter(product => product.prod_id !== prodId);
    setShoppingList(updatedList);
  }

  const getCoordinatesArray = () => {
    return shoppingList.map((product) => ({
      latitude: product.latitude,
      longitude: product.longitude,
      name: product.name,
      img: product.img,
    }));
  };

  const coordinatesArray = getCoordinatesArray();

  const handleSaveList = () => {
    if (user) {
      if (listName.trim() === '') {
        setMessage('');
        return;
      }
      fetch("http://localhost:3001/api/shopping-list", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId: user.userId,
          listName: listName,
          items: shoppingList,
        }),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to save shopping list");
          }
          return response.json();
        })
        .then((data) => {
          setSaveConfirmationMessage(data.message);
          setListName('');
        })
        .catch((error) => {
          console.error("Error saving shopping list:", error);
        });

    } else {
      setUserMessage('Please Sign in to save the shopping list.');
      console.log("User is not logged in. Please log in to save the list.");
    }
  };

  const handleStartNewList = () => {
    setShowConfirmation(true);
  };

  const handleConfirmation = (saveList) => {
    setShowConfirmation(false);

    if (saveList) {
      handleSaveList();
      handleSavePopup();
    } else {
      setShoppingList([]);
    }
  };

  const handleSavePopup = () => {
    setMessage('');
    setListName('');
    setShowSavePopup(true);
    
  };

  const handleClosePopup = () => {
    setShowSavePopup(false);
    setSaveConfirmationMessage('');
    setShoppingList([]);
  };

  const handleSavePopupConfirmation = () => {
    if (listName.trim() === '') {
      setMessage('*please enter a list name.');
      return;
    }
    setShowSavePopup(false);
    handleSaveList();
    setMessage('');
  };

  const renderSaveConfirmationPopup = () => {
    if (saveConfirmationMessage) {
      return (
        <>
          <div className="overlay" />
          <div className="popup">
            <button className="close-button" onClick={handleClosePopup}>
              &times;
            </button>
            <div className="success-mess">
              <h2>{saveConfirmationMessage}</h2>
              <p>Do you want to see all of your shopping lists?</p>
              <a href="/account" className="button">Go to account</a>
            </div>
          </div>
        </>
      );
    }
    return null;
  };


  return (
    <div className="cart-container">
      <div className="title">
        <p>Your Shopping List</p>
      </div>
      {shoppingList.length > 0 && (
        <div className="cart-list">
          <div className="info">
            <div className="info-product">
              <p> Product</p>
            </div>
            <div className="info-price">
              <p>Price/Item</p>
            </div>
            <div className="info-quantity">
              <p>Quantity</p>
            </div>
          </div>

          {shoppingList?.map((product) => (
            <div key={product.prod_id} className="cart-item">
              <div className="product-info">
                <img src={product.img} alt={product.name} />
                <div className="details">
                  <p>{product.name}</p>
                  <p>{product.description} </p>
                </div>
              </div>
              <div className="price">
                <p>{product.price}$</p>
              </div>
              <div className="quantity-controls">
                <button onClick={() => updateQuantity(product.prod_id, product.quantity - 1)}>
                  -
                </button>
                <p>{product.quantity ? product.quantity : 1}</p>
                <button onClick={() => updateQuantity(product.prod_id, product.quantity + 1)}>
                  +
                </button>
              </div>
              <div className="quantity-delete">
                <button onClick={() => removeFromList(product.prod_id)}>
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
      {shoppingList.length > 0 ? (
        <>
          <div className="total">
            Total: {shoppingList.reduce((total, product) => total + product.price * (product.quantity || 1), 0).toFixed(2)}$
          </div>
          <div className="buttons-container">
            <div className="start">
              <button onClick={handleStartNewList}>Start a new List</button>
            </div>

            {showConfirmation && (
              <>
                <div className="overlay" />
                <div className="confirmation-popup">
                  <button className="close-button" onClick={() => setShowConfirmation(false)}>
                    &times;
                  </button>
                  <p>Do you want to save your current shopping list?</p>
                  <button onClick={() => handleConfirmation(true)}>Yes, Save</button>
                  <button onClick={() => handleConfirmation(false)}>No, Discard</button>
                </div>
              </>
            )}

            <div className="save">
              <button onClick={handleSavePopup}>Save List</button>
            </div>

            {showSavePopup && (
              <>
                <div className="overlay" />
                <div className="popup">
                  <button className="close-button" onClick={() => setShowSavePopup(false)}>
                    &times;
                  </button>
                  <p>Enter list name:</p>
                  <input type="text" value={listName} onChange={(e) => setListName(e.target.value)} />
                  {message && <p className="message">{message}</p>}
                  <button onClick={handleSavePopupConfirmation}>Save</button>
                </div>
              </>
            )}
            {renderSaveConfirmationPopup()}
          </div>
          {userMessage && <p className="message1">{userMessage}</p>}
          <div className="map">
            <NonGeographicMap locations={coordinatesArray} />
          </div>
        </>
      ) : (
        <div className="no-items">
          <p>No items in the shopping list.</p>
          <a href="/products" className="button">Start planning your shopping list!</a>
        </div>
      )}
    </div>
  );
};

export default List;