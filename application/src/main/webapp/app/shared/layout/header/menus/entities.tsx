import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/vehicle">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Vehicle
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/vehicle-type">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Vehicle Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/rate">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Rate
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/meta-data">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Meta Data
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/vehicle-details">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Vehicle Details
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/vehicle-model">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Vehicle Model
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/rating">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Rating
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/feed-back">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Feed Back
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/provider">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Provider
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/address">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Address
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
