import React, { Component } from 'react'

export default class NewsList extends Component {
    constructor() {
        super()

    }

    render() {
        return (
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', height: '150px', backgroundColor: '#fff' }}>
                {console.log('-------', this.props)}
                <p>Content of {this.props.tab}</p>

            </div>
        )
    }
}