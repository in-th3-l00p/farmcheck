import './home.scss';

import React from 'react';
import { Carousel, Row, Col, Figure } from 'react-bootstrap';
import TextBox from "../../entities/textbox";

interface FeaturesFigureProps {
  text: string,
  alt: string,
  src: string
}

const FeaturesFigure: React.FC<FeaturesFigureProps> = ({text, alt, src, children}) => {
  return (
    <Figure className="d-flex flex-column justify-content-center">
      <Figure.Image 
        alt={alt}
        className="features-figure-image"
        src={src}
      />
      <Figure.Caption className="text-center">
        <h6>{text}</h6>
        {children}
      </Figure.Caption>
    </Figure>
  )
}

export const Home = () => {
  const images = ["1.jpg", "2.jpg", "3.jpg"]
  const featuresImagesBasePath = "../../../content/images/features/"

  return (
    <div>
      {/* implementing a container so the title can be displayed */}
      <div className="carousel-container">
        <Carousel>
          {images.map((image, index) => (
            <Carousel.Item key={index}>
              <img 
                alt={image} className="carousel-image"
                src={`../../../content/images/carousel/${image}`} 
              />
            </Carousel.Item>
          ))}
        </Carousel> 

        <span className="carousel-text text-center">
          <h1 className="fw-bold">FarmCheck</h1>
          <h3>Automatizam tot ce inseamna ferma</h3>
        </span>
      </div>

      {/* "about us" textbox */}
      <TextBox title="About us" className="text-center">
        <p className="about-text">We are the CyberTech Farmers</p>
        <a className="carrot" href="https://en.wikipedia.org/wiki/Carrot">🥕</a>
      </TextBox>

      {/* "features" textbox */}
      <TextBox title="Features" className="features">
        <Row>
          <Col>
            <FeaturesFigure 
              text="Surveillance" 
              alt="surveillance picture"
              src={featuresImagesBasePath + "surveillance.jpg"}
            >
              We implemented tools that can provide security by
              storing informations about your field
            </FeaturesFigure>
          </Col>

          <Col>
            <FeaturesFigure 
              text="Maintenance" 
              alt="maintenance picture"
              src={featuresImagesBasePath + "maintenance.jpg"}
            >
              You can contact us and we guarantee you that we
              can help you if a problem arises
            </FeaturesFigure>
          </Col>
        </Row>
        <Row>
          <Col>
            <FeaturesFigure
              text="Dictionary"
              alt="dictionary picture"
              src={featuresImagesBasePath + "seed.jpg"}
            >
              We provide a huge wiki with a lot of informations
              about seeds, weather, solis etc.
            </FeaturesFigure>
          </Col>

          <Col>
            <FeaturesFigure
              text="Live Data"
              alt="live data picture"
              src={featuresImagesBasePath + "data.jpg"}
            >
              We are storing data about your soil that 
              you can get at real time
            </FeaturesFigure>
          </Col>
        </Row>
      </TextBox>
    </div>
  );
};

export default Home;
