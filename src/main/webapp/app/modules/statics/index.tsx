import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import About from "app/modules/statics/about/about"

const Routes = () => {
    return (
        <div>
            <ErrorBoundaryRoute path="/about" component={About} />
        </div>
)}

export default Routes