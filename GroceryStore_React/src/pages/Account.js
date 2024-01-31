import React, { useState } from 'react';
import './Account.css';
import validator from 'validator';
import AccountInfo from './AccountInfo';
import { useAuth } from '../hooks/AuthContext';

const Account= () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [username, setUsername] = useState('');
  const [isSignIn, setIsSignIn] = useState(true);
  const [emailError, setEmailError] = useState('');
  const [usernameError, setUsernameError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [message, setMessage] = useState('');
  const [accountDetails, setAccountDetails] = useState(null);
  const { user, login } = useAuth();

  const handleToggleMode = () => {
    setIsSignIn((prevMode) => !prevMode);
  };

  const handleAuth = async () => {
    let emailErrorMessage = '';
    let usernameErrorMessage = '';
    let passwordErrorMessage = '';

    // Frontend validation
    if (!validator.isEmail(email)) {
      emailErrorMessage = '*Invalid email format';
    }

    if (!/^[a-zA-Z0-9]+$/.test(username)) {
      usernameErrorMessage = '*Invalid username format';
    }
    if (password.length < 8) {
      passwordErrorMessage = '*Password must have at least 8 characters';
    }

    setEmailError(emailErrorMessage);
    setUsernameError(usernameErrorMessage);
    setPasswordError(passwordErrorMessage);

    if(email.length===0){
      emailErrorMessage='*Email should not be empty';
      setEmailError(emailErrorMessage);
    }
    if(username.length===0){
      usernameErrorMessage='*Username should not be empty';
      setUsernameError(usernameErrorMessage);
    }
    if(password.length===0){
      passwordErrorMessage='*Password should not be empty';
      setPasswordError(passwordErrorMessage);

    }
    if(isSignIn){
      if (emailErrorMessage || passwordErrorMessage) {
        return;
      }
    }
    else{
      if (emailErrorMessage || passwordErrorMessage || usernameErrorMessage) {
        return;
      }
    }

    var apiUrl = 'http://localhost:3001';
    if(isSignIn) {
      apiUrl += '/api/signin';
    } else {
      apiUrl += '/api/signup';
    }

    try {
      const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, username, password }),
      });
      const data = await response.json();

      if (response.ok) {
        setMessage(data.message);
        if (data.success) {
          if(isSignIn){
            login({ userId: data.user_id, username: data.username, email });
            setAccountDetails({ username: data.username, email });
          }else{
            login({ userId: data.user_id, username: username, email });
            setAccountDetails({ username, email });
          }
          console.log('Authentication successful');
        }
      } else {
        setMessage(data.message);
      }
    } catch (error) {
      console.error('Error making API call:', error);
    }

  };

  return (
    <div className="account-page" style={{ height: 'auto' }}>
      {user? (
        <AccountInfo {...user} />
      ) : (
      <div className="auth-container">
        <div className='isSign'>{isSignIn ? 'Sign In' : 'Sign Up'}</div>
        <form>
          {isSignIn || (
            <>
              <label>Username:</label>
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
                <div className="error">{usernameError}</div>
            </>
          )}

          <label>Email:</label>
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          <div className="error">{emailError}</div>

          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <div className="error">{passwordError}</div>
           

          <button type="button" onClick={handleAuth}>
            {isSignIn ? 'Sign In' : 'Sign Up'}
          </button>
          {message && <p>{message}</p>}
        </form>

        <div className="sign" onClick={handleToggleMode}>
          {isSignIn ? 'Don\'t have an account? Sign Up' : 'Already have an account? Sign In'}
        </div>
      </div>
      )}
    </div>
  );
};

export default Account;
