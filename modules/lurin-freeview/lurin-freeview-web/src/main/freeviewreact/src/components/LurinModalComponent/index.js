/**
*
* LurinFvHeaderComponent
*
*/
import React from 'react';
import {
  Modal,
  Button,
  Glyphicon
} from 'react-bootstrap';
import { FormattedMessage } from 'react-intl';

function LurinModalComponent(props) {
  return (
    <Modal
      show={props.showModal}
      onHide={props.onHideModal}
      container={this}
      aria-labelledby="contained-modal-title"
      className="modal-container"
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title">
        <Glyphicon style={{ color: '#DE350A'}} glyph="exclamation-sign" />
        {props.title}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
          {props.description}
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onConfirm} style={{ background: '#DE350A', color: 'white' }}>
        <FormattedMessage id="campaign.modal.button.delete" defaultMessage='Eliminar'></FormattedMessage>
        </Button>
        <Button onClick={props.closeModal}>
        <FormattedMessage id="campaign.modal.button.cancel" defaultMessage='Cancelar'></FormattedMessage>
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

LurinModalComponent.propTypes = {
  title: React.PropTypes.string,
  description: React.PropTypes.string,
  showModal: React.PropTypes.bool,
  onHideModal: React.PropTypes.func,
  onConfirm: React.PropTypes.func,
  closeModal: React.PropTypes.func
};

export default LurinModalComponent;
