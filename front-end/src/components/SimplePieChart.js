import ProductionType from '../Data/ProductionType'
import React, { Component } from 'react'
import ReactApexChart from 'react-apexcharts'


export default class SimplePieChart extends Component {
    constructor(props) {
      super(props);

      this.state = {
      
        series: [44, 55, 13, 43, 22],
        options: {
          chart: {
            width: 380,
            type: 'pie',
          },
          labels: ProductionType.map(ptype=>ptype.label),
          responsive: [{
            breakpoint: 480,
            options: {
              chart: {
                width: 200
              },
              legend: {
                position: 'bottom'
              }
            }
          }]
        },
      
      
      };
    }

    render() {
      return (
            <div id="chart">
            <ReactApexChart options={this.state.options} series={this.state.series} type="pie" width={380} />
            </div>

            );
        }
    }
