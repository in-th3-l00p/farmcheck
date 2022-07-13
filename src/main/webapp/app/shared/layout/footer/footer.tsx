import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content text-center pt-4 pb-2">
    <h3 className="fw-bold mb-4">CyberTech Farmers</h3>
    <h4 className="fw-normal mb-3">Find us on</h4>
    <span className="copyright">
      <div className="line" />
      <p className="fw-bold">© All Rights Reserved</p>
    </span>
  </div>
);

export default Footer;
