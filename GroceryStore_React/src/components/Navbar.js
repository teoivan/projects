import { useRef } from "react";
import { FaBars, FaTimes } from "react-icons/fa";
import "../styles/main.css";
import Logo from "../assets/shop-smart-high-resolution-logo-transparent.png";
import { Link } from "react-router-dom";

function Navbar() {
  const navRef = useRef();

  const showNavbar = () => {
    navRef.current.classList.toggle("responsive_nav");
  };

  return (
    <header>
      <nav ref={navRef}>
        <Link to="/" className="logo-link">
          <img src={Logo} alt="logo" className="logo" />
        </Link>
        <Link to="/">HOME</Link>
        <Link to="/products">PRODUCTS</Link>
        <Link to="/search">SEARCH</Link>
        <Link to="/account">ACCOUNT</Link>
        <Link to="/list">SHOPPING LIST</Link>
        <Link to="/contact">CONTACT</Link>
        <button className="nav-btn nav-close-btn" onClick={showNavbar}>
          <FaTimes/>
        </button>
      </nav>
      <button className="nav-btn" onClick={showNavbar}>
        <FaBars />
      </button>
    </header>
  );
}

export default Navbar;
