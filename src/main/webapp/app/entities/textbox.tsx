// defining a costume component for displaying text without repeating

import React from 'react';
import { Container } from "react-bootstrap"

interface TextBoxProps {
    title: string,
    className?: string
}

const TextBox: React.FC<TextBoxProps> = ({title, children, className}) => {
    return (
        <Container className={`text-box my-4 p-5 ${className}`}>
            <h2>{title}</h2> 
            {children}
        </Container>
    )
}

TextBox.defaultProps = {
    className: ""
}

export default TextBox