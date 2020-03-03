import React, { Component } from 'react';
//import FormDialog from './components/FormDialog';
import Dropdown from './components/Dropdown';
import { UserConsumer } from './UserContext';
import {Table} from 'react-bootstrap'
import MyButton from './components/MyButton';
import ReactDOM from 'react-dom';
import SimpleGraph from './components/SimpleGraph';
import SimplePieChart from './components/SimplePieChart';

//import DatePicker from 'react-datepicker'
//import Container from 'react-bootstrap/Container'

const btnStyle ={
    marginLeft:10,
    width:150,
    background:'#29c596'
}



class Main extends Component {
    constructor(props) {
		super(props)


		this.state = {
			area: "",
			type: "",
            productionType: "",     
            year:"",
            month:"",
            day: "",
            resolution:"",
            YearSelect:true,
            MonthSelect:false,
            DaySelect:false
            
		}
        this.callBack = this.callBack.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        this.fakeSubmit = this.fakeSubmit.bind(this)
        this.checkValidation = this.checkValidation.bind(this)
    }


    setArea = (value,label) => {
        //console.log("Area:", childData)
        this.setState({area: label},()=>console.log("Area:",this.state))
    };
    setType = (value,label) => {
        //console.log("Type:", childData)
        this.setState({type: value},()=>console.log("Type:",this.state))
    };  
    setProductionType = (value,label) => {
        //console.log("ProductionType:", childData)
        this.setState({productionType: label},()=>console.log("ProductionType:",this.state))
    };
    setResolution = (value,label) => {
        //console.log("ProductionType:", childData)
        this.setState({resolution: label},()=>console.log("resolution:",this.state))
    };
    setDate(newDate){
        this.setState({date: newDate})
    };
    setYear = (value,label) => {
        //console.log("Year:", childData)
        this.setState({year: label},()=>console.log("Year:",this.state))
    };
    setMonth = (value,label) => {
        //console.log("Month:", childData)
        this.setState({month: value},()=>console.log("Month:",this.state))
    };
    setDay = (value,label) => {
        //console.log("Year:", childData)
        this.setState({day: label},()=>console.log("Day:",this.state))
    };

    getDaysFromMonts(){
        if(this.state.month===""){
            return "31"
        }
        if(this.state.month==="2"){
            return "29"
        }
        if((this.state.month<8 && this.state.month%2===1)||(this.state.month>=8 && this.state.month%2===0)){
            return "31"
        }
        else{
            return "30"
        }

    }

    callBack(name){
        switch (name) {
            case 'Year':
                this.setState({
                    YearSelect:true,
                    MonthSelect:false,
                    DaySelect:false
                  })
                break;
            case 'Month':
                this.setState({
                    YearSelect:false,
                    MonthSelect:true,
                    DaySelect:false
                  })
                break;
            case 'Day':
                this.setState({
                    YearSelect:false,
                    MonthSelect:false,
                    DaySelect:true
              })
                break;
            default:
                break;
        }
    };

    handleSubmit1(){
        console.log(localStorage.getItem('token'))
        let url = 'https://localhost:8765/energy/api/'+this.state.area+"/"+this.state.type+"/";
        if(this.state.type==="AggregatedGenerationPerType"){
            url += this.state.productionType+"/"
        }
        url += this.state.resolution +"/" 
        if(this.state.YearSelect){
            url +=  "year/"+this.state.year
        }
        if(this.state.MonthSelect){
            url +=  "month/"+this.state.year+"-"+this.state.month
        }
        if(this.state.DaySelect){
            url +=  "date/"+this.state.year+"-"+this.state.month+"-"+this.state.day
        }
        console.log("url:",url)



    };

    checkValidation(){
        
        let area = this.state.area
        let type = this.state.type
        let productionType = this.state.productionType     
        let year = this.state.year
        let month = this.state.month
        let day = this.state.day
        let resolution =this.state.resolution
        let YearSelect = this.state.YearSelect
        let MonthSelect = this.state.MonthSelect
        let DaySelect = this.state.DaySelect
    
        if(type==='' || area ===''|| year===''|| resolution===''){
            return false
        }
        if(type==="AggregatedGenerationPerType" && productionType ===''){
            return false
        }
        if(MonthSelect && month===''){
            return false
        }
        if(DaySelect && (month===''||day==='')){
            return false
        }
        return true
    }
    handleSubmit() {
        let dateArg = ""
        console.log('Hopfully submiting ', this.state);

        if(!this.checkValidation()){
            alert("Please fill all the requierd fields")
            return
        }
        console.log(localStorage.getItem('token'))
            let url = 'https://localhost:8765/energy/api/'+this.state.type+"/"+this.state.area+"/";
            if(this.state.type==="AggregatedGenerationPerType"){
                url += this.state.productionType+"/"
            }
            url += this.state.resolution +"/" 
            if(this.state.YearSelect){
                dateArg="year"
                url +=  "year/"+this.state.year
            }
            if(this.state.MonthSelect){
                dateArg="month"
                url +=  "month/"+this.state.year+"-"+this.state.month
            }
            if(this.state.DaySelect){
                dateArg="date"
                url +=  "date/"+this.state.year+"-"+this.state.month+"-"+this.state.day
            }
            url += "?type=json"
            console.log("url:",url)
        
        fetch(url,{
            method: 'GET',
            headers: {
                'Content-Type':'application/x-www-form-urlencoded',
                "Token": localStorage.getItem('token')
            }
        }).then((response) =>  response.json())
        .then(json => {    
            
            console.log("IM HERE:",json);
            if(json.length>0){
                ReactDOM.render(this.createGraph(json,dateArg),document.getElementById('graph')) 
            }
            else{
                alert("There is no Data for your request")
                ReactDOM.render(<div></div>,document.getElementById('graph'))
            }
            
    

        }); 

    }
    fakeSubmit(){
     
        let url = "https://localhost:8765/energy/api/AggregatedGenerationPerType/Greece/AllTypes/PT60M/date/2018-1-1?type=json"
        fetch(url,{
            method: 'GET',
            headers: {
                'Content-Type':'application/x-www-form-urlencoded',
                "Token": localStorage.getItem('token')
            }
        }).then((response) =>  response.json())
        .then(json => {    
            
            console.log("Response json:",json);
            
     

        }); 
    }

    createGraph(file,dateArg){
        return( <SimpleGraph date = {dateArg} input = {file}/>)
      
    }


    render(){
        return (
            <div >
                <div className='row'>
                    <h1>
                        Main page
                    </h1>                
                </div>
                <div className="row">
                    <UserConsumer>
                        { context =>
                        <Table >
                            <tbody>
                                <tr>
                                    <td><Dropdown  value = {this.setType} type = "Type" />
                                    {this.state.type==="AggregatedGenerationPerType"?
                                    <Dropdown  value = {this.setProductionType} type = "ProductionType" />:null}</td>
                                    <td><Dropdown  value = {this.setArea} type = "Area" /></td>  
                                    <td><Dropdown  value = {this.setResolution} type = "Resolution" /></td>
                                </tr>
                                <tr>
                                    <td><MyButton callBack = {this.callBack} select = {this.state.YearSelect} name = 'Year'/></td>
                                    <td><MyButton callBack = {this.callBack} select = {this.state.MonthSelect} name = 'Month'/></td>
                                    <td><MyButton callBack = {this.callBack} select = {this.state.DaySelect} name = 'Day'/></td>

                                </tr>
                                <tr>
                                    <td><Dropdown  value = {this.setYear} type = "Year" /></td>
                                    {(this.state.MonthSelect || this.state.DaySelect)?<td><Dropdown  value = {this.setMonth}  type = "Month" /></td>:null}
                                    {this.state.DaySelect?<td><Dropdown  value = {this.setDay} month = {this.getDaysFromMonts()} type = "Day" /></td>:null}
                                </tr>
                                <tr>
                                    <td></td><td></td>
                                    <td><button onClick = {this.handleSubmit} style = {btnStyle} className="btn btn-info">Submit</button></td>
                                </tr>
                                {/*<tr><td><button onClick = {this.fakeSubmit} style = {btnStyle} className="btn btn-info">Submit</button></td></tr>*/}

                        </tbody>
                    </Table>
                }
                </UserConsumer>

                    
                </div>
                
                <div id="graph"></div>
            </div>
        );
    }
    
}

export default Main;
