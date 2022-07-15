import './header.scss';

import React, { useState } from 'react';
import { Translate, Storage } from 'react-jhipster';
import { Navbar, NavbarToggler, Collapse, Nav, NavItem, NavLink, NavbarBrand, Container  } from 'reactstrap';
// import LoadingBar from 'react-redux-loading-bar';

import { Home, Brand } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu, LocaleMenu } from '../menus';
import { useAppDispatch } from 'app/config/store';
import { setLocale } from 'app/shared/reducers/locale';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  currentLocale: string;
}

export interface LinkProps {
  href: string;
  text: string;
}

const Link = (props: LinkProps) => {
  const navLinkStyle = { color: "#f2f2f2" }
  return (
    <NavItem>
      <NavLink style={navLinkStyle} href={props.href}>
        {props.text}
      </NavLink>
    </NavItem>
  )
}


const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  const dispatch = useAppDispatch();

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    dispatch(setLocale(langKey));
  };

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      <Navbar expand="md" dark={true}>
        <NavbarBrand href="/" className="navbar-brand">FarmCheck</NavbarBrand>
        <NavbarToggler onClick={toggleMenu} />
        <Collapse isOpen={menuOpen} navbar>
          <Nav className="me-auto" navbar>
            <Link href="/shop" text="Shop" />
            <Link href="/download" text="Download" />
            <Link href="/wiki" text="Crop wiki" />
            <Link href="/about" text="About us" />
          </Nav> 
          <Nav navbar>
            <Link href="/account/login" text="Login" />
            <Link href="/account/register" text="Register" />
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  )
};

export default Header;
