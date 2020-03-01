import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import  Dropdown, { DropArea, DropPType }  from './Dropdown';

export default function FormDialog() {
	const [open, setOpen] = React.useState(true);

	const handleClickOpen = () => {
		setOpen(true);
	};

	const handleClose = () => {
		setOpen(false);
	};
	

	return (
		<React.Fragment>
			<Button variant="outlined" color="primary" onClick={handleClickOpen}>
				New Request
			</Button>
			<Dialog  open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
				<DialogTitle id="form-dialog-title">Request Data</DialogTitle>
				<DialogContent>
					<Dropdown type = "Type" />
				</DialogContent>
				<DialogContent>
					<DialogContentText>
						<DropArea/>
					</DialogContentText>
				</DialogContent>
				<DialogContent >					
					<DropPType /> 
				</DialogContent>
				<DialogActions>
					<Button onClick={handleClose} color="primary">
						Cancel
					</Button>
					<Button onClick={handleClose} color="primary">
						Ok
					</Button>
				</DialogActions>
			</Dialog>
		</React.Fragment>
	);
}

