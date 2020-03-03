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
            
              categories: []
            }
          },
          series: [
            {
              name: "",
              data: []
            }
          ]
        };
      }

      componentDidMount(){
        console.log(this.props.input[0].Dataset)
        
          if(this.props.date==="date"){
            this.setState({
                options:{
                    xaxis:{
                        categories:this.props.input.map(jobject=>jobject.DateTimeUTC.substring(11,16))
                    }
                }
              })
            if(this.props.input[0].Dataset==="ActualTotalLoad"){
                this.setState({
                    
                    
                    series: [{
                        name: this.props.input[0].Dataset,
                        data: this.props.input.map(jobject =>jobject.ActualTotalLoadValue)
                    }]   
                })
              }
              if(this.props.input[0].Dataset==="DayAheadTotalLoadForecast"){
                this.setState({
                    
                    series: [{
                        name: this.props.input[0].Dataset,
                        data: this.props.input.map(jobject =>jobject.DayAheadTotalLoafForecastValue)
                    }]   
                })
              }
              if(this.props.input[0].Dataset==="ActualVSForecastedTotalLoad"){
                this.setState({
                    
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
                    
                    series: [
                        {
                            name: "Actual Total Load",
                            data: this.props.input.map(jobject =>jobject.AggregatedGenerationPerType)
                        }
                    ]   
                }) 
              }
          }
          if(this.props.date==="month"){
            this.setState({
                options:{
                    xaxis:{
                        categories:this.props.input.map(jobject=>jobject.Day)
                    }
                }
              })
            if(this.props.input[0].Dataset==="ActualTotalLoad"){
                this.setState({
                    
                    
                    series: [{
                        name: this.props.input[0].Dataset,
                        data: this.props.input.map(jobject =>jobject.ActualTotalLoadByDayValue)
                    }]   
                })
              }
              if(this.props.input[0].Dataset==="DayAheadTotalLoadForecast"){
                this.setState({
                    
                    series: [{
                        name: this.props.input[0].Dataset,
                        data: this.props.input.map(jobject =>jobject.DayAheadTotalLoafForecastByDayValue)
                    }]   
                })
              }
              if(this.props.input[0].Dataset==="ActualVSForecastedTotalLoad"){
                this.setState({
                    
                    series: [
                        {
                            name: "Actual Total Load",
                            data: this.props.input.map(jobject =>jobject.ActualTotalLoadByDayValue)
                        },
                        {
                            name: "Day Ahead Forecast",
                            data: this.props.input.map(jobject =>jobject.DayAheadTotalLoafForecastByDayValue)
                        }
                    ]   
                }) 
              }  
              if(this.props.input[0].Dataset==="AggregatedGenerationPerType"){
                this.setState({
                    
                    series: [
                        {
                            name: "Aggrigated "+this.props.input[0].ProductionType,
                            data: this.props.input.map(jobject =>jobject.ActualGenerationOutputByDayValue )
                        }
                    ]   
                }) 
              }
          }
          if(this.props.date==="year"){
            this.setState({
                options:{
                    xaxis:{
                        categories:this.props.input.map(jobject=>jobject.ActualTotalLoadByMonthValue)
                    }
                }
              })
            if(this.props.input[0].Dataset==="ActualTotalLoad"){
                this.setState({     
                    series: [{
                        name: this.props.input[0].Dataset,
                        data: this.props.input.map(jobject =>jobject.ActualTotalLoadByMonthValue)
                    }]   
                })
              }
              if(this.props.input[0].Dataset==="DayAheadTotalLoadForecast"){
                this.setState({
                    
                    series: [{
                        name: this.props.input[0].Dataset,
                        data: this.props.input.map(jobject =>jobject.DayAheadTotalLoafForecastByMonthValue)
                    }]   
                })
              }
              if(this.props.input[0].Dataset==="ActualVSForecastedTotalLoad"){
                this.setState({
                    
                    series: [
                        {
                            name: "Actual Total Load",
                            data: this.props.input.map(jobject =>jobject.ActualTotalLoadByMonthValue)
                        },
                        {
                            name: "Day Ahead Forecast",
                            data: this.props.input.map(jobject =>jobject.DayAheadTotalLoafForecastByMonthValue)
                        }
                    ]   
                }) 
              }  
              if(this.props.input[0].Dataset==="AggregatedGenerationPerType"){
                this.setState({
                    
                    series: [
                        {
                            name: "Actual Total Load",
                            data: this.props.input.map(jobject =>jobject.AggregatedGenerationPerTypeByMonthValue)
                        }
                    ]   
                }) 
              }
          }
          
          

          console.log( )
          console.log()
      }

    
    render() {
        console.log("Rendet Graph")
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
