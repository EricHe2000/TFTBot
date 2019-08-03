import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class Button extends React.Component {
    constructor(props) {
        super(props);
        this.state = { isStarted: false };

        // This binding is necessary to make `this` work in the callback
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        if(this.state.isStarted){
            fetch('/stop')
            .then(response => response.text())
            .then(message => {    
                console.log(message) ;               
                var msg = JSON.parse(message);
                console.log(msg.status);
                if(!msg.status){
                    this.props.parentCallback('Bot has been stopped')
                    this.setState(prevState => ({
                        isStarted: !prevState.isStarted
                    }));
                }
               else{
                    this.props.parentCallback('Failed to Start');

               }

            });
        }
        else{
            fetch('/start')
            .then(response => response.text())
            .then(message => {    
                console.log(message) ;               
                var msg = JSON.parse(message);
                console.log(msg.status);
                if(msg.status){
                    this.props.parentCallback('Bot has been started')
                    this.setState(prevState => ({
                        isStarted: !prevState.isStarted
                    }));
                }
               else{
                    this.props.parentCallback('Failed to Start');

               }

            });
        }     
    }

    render() {
        const {
            variant,
            content,
            ...others
        } = this.props;
        return (
            <button className={variant} onClick={this.handleClick}>
                {this.state.isStarted ? 'Stop' : 'Start'}
            </button>
        );
    }
}

class MonitorButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = { isToggleOn: true };

        // This binding is necessary to make `this` work in the callback
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        fetch('/monitor')
            .then(response => response.text())
            .then(message => {
                this.setState({ message: message });
            });
    }

    render() {
        const {
            variant,
            content,
            ...others
        } = this.props;
        return (
            <button className={variant} onClick={this.handleClick}>
                Monitor
            </button>
        );
    }
}

class App extends Component {
    state = {
        message: "Bot Status"
    }

    callbackFunction = (childData) => {
        this.setState({ message: childData })
    };

    render() {
        return (
            <div className="App">
                <h1 className="App-title">{this.state.message}</h1>
                <img src='/images/upload.jpg' />
                <Button parentCallback={this.callbackFunction} />
                <MonitorButton variant="green" />
            </div>
        );
    }
}

export default App;