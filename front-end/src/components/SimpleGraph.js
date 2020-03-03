import React, { Component } from 'react'
import Chart from 'react-apexcharts'

export default class SimpleGraph extends Component {
    
    constructor(props) {
        super(props);
    
        this.state = {
          options: {
            chart: {
              id: "basic-bar"
            },
            xaxis: {
            
              categories: [1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999]
            }
          },
          series: [
            {
              name: "series-1",
              data: [30, 40, 45, 50, 49, 60, 70, 91]
            }
          ]
        };
      }

      componentDidMount(){
        console.log(this.props.input[0].Dataset)
          if(this.props.input[0].Dataset==="ActualTotalLoad"){
            this.setState({
                options:{
                    xaxis:{
                        categories:this.props.input.map(jobject=>jobject.DateTimeUTC.substring(11,16))
                    }
                },
                
                series: [{
                    name: this.props.input[0].Dataset,
                    data: this.props.input.map(jobject =>jobject.ActualTotalLoadValue)
                }]   
            })
          }
          if(this.props.input[0].Dataset==="DayAheadTotalLoadForecast"){
            this.setState({
                options:{
                    xaxis:{
                        categories:this.props.input.map(jobject=>jobject.DateTimeUTC.substring(11,16))
                    }
                },
                
                series: [{
                    name: this.props.input[0].Dataset,
                    data: this.props.input.map(jobject =>jobject.DayAheadTotalLoafForecastValue)
                }]   
            })
          }
          if(this.props.input[0].Dataset==="ActualVSForecastedTotalLoad"){
            this.setState({
                options:{
                    xaxis:{
                        categories:this.props.input.map(jobject=>jobject.DateTimeUTC.substring(11,16))
                    }
                },
                
                series: [
                    {
                        name: "Actual Total Load",
                        data: this.props.input.map(jobject =>jobject.ActualTotalLoadValue)
                    },
                    {
                        name: "Day Ahead Forecast",
                        data: this.props.input.map(jobject =>jobject.DayAheadTotalLoafForecastValue)
                    }
                ]   
            }) 
          }
            
          if(this.props.input[0].Dataset==="AggregatedGenerationPerType"){
            this.setState({
                options:{
                    xaxis:{
                        categories:this.props.input.map(jobject=>jobject.DateTimeUTC.substring(11,16))
                    }
                },
                
                series: [
                    {
                        name: "Actual Total Load",
                        data: this.props.input.map(jobject =>jobject.AggregatedGenerationPerType)
                    }
                ]   
            }) 
          }
          

          console.log( )
          console.log()
      }

    
    render() {
        return (
            <div className="app">
                <div className="row">
                    <div className="mixed-chart">
                        <Chart
                        options={this.state.options}
                        series={this.state.series}
                        type="bar"
                        width="1200"
                        
                        />
                </div>
            </div>
        </div>
        )
    }
}
