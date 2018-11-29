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
import { FormattedMessage, intlShape  } from 'react-intl';
import { ITEM_THEME } from '../../utils/theme';

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
        <Glyphicon style={{ color: props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_BUTTON_DELETE] }} glyph="exclamation-sign" />
        {props.title}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
          {props.description}
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onConfirm} style={{ background: props.currentStyles[ITEM_THEME.BACKGROUNDCOLOR_BUTTON_DELETE], color: 'white' }}>
            <FormattedMessage id="modal.button.delete" defaultMessage='Eliminar'></FormattedMessage>
        </Button>
        <Button onClick={props.closeModal}>
            <FormattedMessage id="modal.button.cancel" defaultMessage='Cancelar'></FormattedMessage>
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
  closeModal: React.PropTypes.func,
  currentStyles: React.PropTypes.object
};

export default LurinModalComponent;
