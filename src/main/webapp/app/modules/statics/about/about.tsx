import './about.scss'

import React from 'react';
import { Container } from 'reactstrap'
import TextBox from "../../../entities/textbox"

const About = () => {
    return (
        <Container>
            <TextBox title="About Us">
                <p>
                    FarmCheck was developed under the {'"'}Descopera-ti 
                    pasiunea in IT academy{'"'}, in 2022, by the CyberTech Farmers
                    team.
                </p>
                <p className="text-decoration-underline">The members of the team are</p>
                <ul>
                    <li>Tisca Catalin</li>
                    <li>Alexandru Bleoto</li>
                    <li>Eduard Serban</li>
                    <li>Dan Cristian George</li>
                    <li>Russu Mihaela</li>
                    <li>Bene Ionut</li>
                </ul>
            </TextBox>
        </Container>
)}

export default About