import React, {Component}from 'react';
import Button from '@material-ui/core/Button';
//import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
//import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import  Dropdown, { DropArea, DropPType }  from './Dropdown';


//const [open, setOpen] = React.useState(true);






export default class FormDialog extends Component {
	constructor(props) {
		super(props)


		this.state = {
			open: true,
			area: "",
			type: "",
			productionType: ""
		}
		this.handleClickOpen = this.handleClickOpen.bind(this)
		this.handleClose = this.handleClose.bind(this)
		this.handleOk = this.handleOk.bind(this)
		this.setArea = this.setArea.bind(this)
		this.setType = this.setType.bind(this)
	
	}
	handleClickOpen(){
		this.setState((prevState) => {
			return {open:!prevState.open};
		  })
	};
	handleClose(){
		this.setState((prevState) => {
			return {open: !prevState.open};
		  })
	};
	handleOk(){
		this.setState((prevState) => {
			return {open: !prevState.open};
		  })
		console.log(this.state.area)
	};
	setArea = (childData) => {
		console.log("Area:", childData)
		this.setState({area: childData})
	};
	setType = (childData) => {
		console.log("Type:", childData)
		this.setState({type: childData})}
		
	setArea = (childData) => {
		console.log("ProductionType:", childData)
		this.setState({productionType: childData})
	};

	
	render() {
		return (
			<React.Fragment>
				<Dropdown value = {this.setPType} type = "ProductionType" />
				<Button variant="outlined" color="primary" onClick={this.handleClickOpen}>
					New Request
				</Button>
				<Dialog  open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
					<DialogTitle id="form-dialog-title">Request Data</DialogTitle>
					<DialogContent>
						<Dropdown  value = {this.setType} type = "Type" />
					</DialogContent>
					{this.state.type==="AggregatedGenerationPerType"?
					<DialogContent>
						<Dropdown value = {this.setPType} type = "ProductionType" />
					</DialogContent>:null}
					<DialogContent>
						<Dropdown value = {this.setArea} type = "Area" />
					</DialogContent>
					
					<DialogActions>
						<Button onClick={this.handleClose} color="primary">
							Cancel
						</Button>
						<Button onClick={this.handleOk} color="primary">
							Ok
						</Button>
					</DialogActions>
				</Dialog>
			</React.Fragment>
		);
	}
}
