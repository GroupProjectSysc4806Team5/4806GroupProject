import logo from './logo.svg';
import './App.css';
import SearchBar from "./components/SearchBar/SearchBar";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Amazin Online Bookstore
        </p>
        <SearchBar></SearchBar>
      </header>
    </div>

  );
}

export default App;
