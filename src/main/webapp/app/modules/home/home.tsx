import './home.scss';

import React from 'react';
import { Carousel } from 'react-bootstrap';

export const Home = () => {
  const imagesBasePath = "../../../content/images/carousel/"
  const images = ["1.jpg", "2.jpg", "3.jpg"]

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
    </div>
  );
};

export default Home;
