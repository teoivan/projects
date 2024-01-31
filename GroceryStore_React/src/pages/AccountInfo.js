import React, { useState, useEffect } from 'react';
import "./AccountInfo.css";
import { useAuth } from '../hooks/AuthContext';
import NonGeographicMap from "../components/Map";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';


const AccountInfo = ({ username, email }) => {
  const { logout, user } = useAuth();
  const [shoppingLists, setShoppingLists] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [selectedList, setSelectedList] = useState([]);
  const [products, setProducts] = useState([]);
  const [checkedProducts, setCheckedProducts] = useState([]);
  const [productLocations, setProductLocations] = useState([]);
  const [listsToShow, setListsToShow] = useState(3);
  const [loadingMore, setLoadingMore] = useState(false);

  const handleLogout = () => {
    logout();
  };

  const handleListClick = async (list) => {
    try {
      const response = await fetch(`http://localhost:3001/api/list/${list.list_id}/products`);
      const data = await response.json();
      console.log('Products for the selected list:', data);
      setProducts(data);
      const locations = data.map((product) => ({
        latitude: product.latitude,
        longitude: product.longitude,
        name: product.name,
        img: product.img,
      }));
      setProductLocations(locations);
    } catch (error) {
      console.error('Error fetching products for the selected list:', error);
    }
    setSelectedList(list);

    setShowPopup(true);
  };

  const closePopup = () => {
    setShowPopup(false);
    setSelectedList([]);
  };

  useEffect(() => {
    const fetchShoppingLists = async () => {
      try {
        const response = await fetch(`http://localhost:3001/api/shopping-lists/${user.userId}`);
        const data = await response.json();
        console.log('Shopping Lists:', data);
        setShoppingLists(data);
      } catch (error) {
        console.error('Error fetching shopping lists:', error);
      }
    };

    fetchShoppingLists();
  }, [user.userId]);

  const handleCheckboxChange = (productId) => {
    setCheckedProducts((prevCheckedProducts) => {
      if (prevCheckedProducts.includes(productId)) {
        return prevCheckedProducts.filter((id) => id !== productId);
      } else {
        return [...prevCheckedProducts, productId];
      }
    });
  };

  const handleLoadMore = () => {
    setListsToShow((prevListsToShow) => prevListsToShow + 3);
  };

  const handleLoadLess = () => {
    setListsToShow((prevListsToShow) => Math.max(3, prevListsToShow - 3));
  };

  let dateContent = null;
  if (selectedList) {
    const dateObject = new Date(selectedList.created_at);
    const year = dateObject.getFullYear();
    const month = (dateObject.getMonth() + 1).toString().padStart(2, '0');
    const day = dateObject.getDate().toString().padStart(2, '0');
    const hours = dateObject.getHours().toString().padStart(2, '0');
    const minutes = dateObject.getMinutes().toString().padStart(2, '0');
    const seconds = dateObject.getSeconds().toString().padStart(2, '0');

    dateContent = (
      <div className='date'>
        <p>Date created: {year}-{month}-{day}</p>
        <p>Time created: {hours}:{minutes}:{seconds}</p>
      </div>
    );
  }




  return (
    <div className='account'>
      <div className='detail'>
        <h2>Account Details</h2>
        <p>Username: {username}</p>
        <p>Email: {email}</p>
        <button onClick={handleLogout}>Logout</button>
      </div>

      {shoppingLists.length > 0 ? (
        <>
          <h3>Shopping Lists:</h3>
          <div className='list'>
            {shoppingLists.slice(0, listsToShow).map((list) => (
              <li key={list.list_id} onClick={() => handleListClick(list)}>{list.list_name}</li>
            ))}
          </div>

          <div className='but'>
            {shoppingLists.length > listsToShow && (
              <button onClick={handleLoadMore} disabled={loadingMore}>
                <FontAwesomeIcon icon={faArrowDown} />
              </button>
            )}
            <button onClick={handleLoadLess} disabled={loadingMore || listsToShow <= 3}>
              <FontAwesomeIcon icon={faArrowUp} />
            </button>
          </div>

        </>
      ) : (
        <div style={{ textAlign: 'center' }}>
          <p style={{ fontSize: '20px' }} className='no-shopping'>
            No shopping lists created yet!
          </p>
          <a href="/products" className="button">Start creating your first shopping list</a>
        </div>
      )}
      {showPopup && (
        <>
          <div className="overlay" />
          <div className="confirmation">
            <button className="close-button" onClick={() => closePopup()}>
              &times;
            </button>
            <h3>{selectedList.list_name}</h3>
            <div className="inf">
              <div className="inf-product">
                <p> Product</p>
              </div>
              <div className="inf-price">
                <p>Price/Item</p>
              </div>
              <div className="inf-quantity">
                <p>Quantity</p>
              </div>
            </div>
            <ul>
              {products.map((product) => (
                <li key={product.prod_id}>
                  <div className="product_info" >
                    <input
                      type="checkbox"
                      checked={checkedProducts.includes(product.prod_id)}
                      onChange={() => handleCheckboxChange(product.prod_id)}
                    />
                    <img src={product.img} alt={product.name} />
                    <div className="details">
                      <p>{product.name}</p>
                      <p>{product.description}</p>
                    </div>
                  </div>
                  <div className="price">
                    <p>{product.price}$</p>
                  </div>
                  <div className="quantity">
                    <p>{product.quantity}</p>
                  </div>
                </li>
              ))}
            </ul>
            <div className="tot">
              Total: {products.reduce((total, product) => total + product.price * (product.quantity || 1), 0).toFixed(2)}$
            </div>
            {dateContent}

            <NonGeographicMap locations={productLocations} />
          </div>

        </>
      )}
    </div>
  );
};

export default AccountInfo;
