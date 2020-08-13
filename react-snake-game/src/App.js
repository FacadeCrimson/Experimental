import React from 'react';
import './App.css';

class Square extends React.Component {

  render() {
    return (
      <button className="square" style={(this.props.color==="#fff")?{}:{background: this.props.color}}>
      </button>
    );
  }
}

class Board extends React.Component {

  renderSquare(i,j, color = "#fff") {
    return <Square key ={[i,j].toString()} color = {color} />
  }

  render() {
    const playground = [];
    var pos = 0;
    var len = this.props.len;
    for(let i = 0; i < len; i++){
  
      const row = [];
      
      for(let j = 0; j < 10; j++){
        if(this.props.snake.includes(pos)){
          row.push(this.renderSquare(i,j,"#060606"))
          pos+=1;
          continue;
        }
        if(this.props.food === pos){
          row.push(this.renderSquare(i,j,"gold"))
          pos+=1;
          continue;
        }
        row.push(this.renderSquare(i,j))
        pos+=1;
      }
      const newRow =  <div className="board-row" key = {i}>{row}</div>
      playground.push(newRow)
    }

    let boardStyle = {
      opacity: 1.0
    };
      if (!this.props.game) {
        boardStyle = {
          opacity:0.5
        }  
    }

    return (
      <div style={boardStyle}>
        {playground}
      </div>
    );
  }
}

class Control extends React.Component{
  render() {
    return (
      <button className="control" onClick={() => this.props.onClick()}>
      </button>
    );
  }
}

function Warning(props){
  if(props.game){
    return null;
  }
  return (<div className="warning"><h1>Your game ends!</h1>
          <h1>Your score is {props.snake.length}.</h1></div>)

}

class Game extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      len: 10,
      snake: [0],
      food:undefined,
      game: true,
    };
  }

  generateFood(){
    var newFood = undefined;
    do{
      newFood = Math.floor(Math.random() * Math.pow(10,2))
    }
      while(this.state.snake.includes(newFood));
      this.setState(({
        food: newFood
      })) 
  }

  eatFood(food){
    var newSnake = this.state.snake.slice();
    newSnake.unshift(food);
    this.setState({snake: newSnake})
  }

  handleKeyPress = (event) => {
    if(!this.state.game){
      return;
    }
    var addition = this.state.len
    var newPos = undefined;
    var food =this.state.food;
    var newSnake = this.state.snake.slice();
    switch (event.key) {
    case "ArrowDown":
      newPos = newSnake[0]+addition;
      break;
    case "ArrowUp":
      newPos = newSnake[0]-addition;
      break;
    case "ArrowLeft":
      newPos = newSnake[0]-1; 
      if (newPos%10===9){
        this.setState({game:false})
        return;
      }
      break;
    case "ArrowRight":
      newPos = newSnake[0]+1;
      if (newPos%10===0){
        this.setState({game:false})
        return;
      }
      break;
    default:
      this.setState({game:false})
      return;
    }
    if (newPos === food){
      this.eatFood(food);
      this.generateFood();
      console.log(this.state.snake)
      return;
    }
    if (newPos < 0 || newSnake.includes(newPos) || newPos > Math.pow(10,2)-1){
      this.setState({game:false})
      return;
    }
    newSnake.unshift(newPos);
    newSnake.pop();
    this.setState({snake: newSnake})
  }

  componentDidMount(){
    this.generateFood();
    document.addEventListener("keydown", this.handleKeyPress, false);
  }

  componentWillUnmount(){
    document.removeEventListener("keydown", this.handleKeyPress, false);
  }

  reset(){
    this.setState({snake: [0],game:true,});
    this.generateFood();
  }

  render() {
    return (
      <div className="game">
        <div className="game-board">
          <Warning game={this.state.game} snake={this.state.snake}/>
          <Board snake={this.state.snake} len ={this.state.len} food={this.state.food} game={this.state.game}/>
        </div>
        <div className="side-board">
          <h2 className="title"> Reset game </h2>
          <Control onClick={()=>this.reset()}/>
        </div>
      </div>
    );
  }
}


function App() {
  
  return (
    <Game />
  )
} 

export default App;
