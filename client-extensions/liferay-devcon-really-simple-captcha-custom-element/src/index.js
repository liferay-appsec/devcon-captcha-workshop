/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, { useState } from 'react';
import ReactDOM from 'react-dom';

export default function App() {
  const [isChecked, setIsChecked] = useState(false);

  const handleCheckboxChange = (event) => {
    setIsChecked(event.target.checked);
  };

  return (
    // Main widget container styled to resemble a classic CAPTCHA box
    <div
      style={{
        display: 'inline-block',
        padding: '4px',
        backgroundColor: '#f9f9f9',
        border: '1px solid #ccc',
        borderRadius: '3px',
        fontFamily: 'Arial, sans-serif',
        boxShadow: '0 0 5px rgba(0, 0, 0, 0.1)',
      }}
    >
      <div
        style={{
          display: 'flex',
          alignItems: 'center',
          padding: '8px',
        }}
      >
        <input
          id="visible-checkbox"
          type="checkbox"
          checked={isChecked}
          onChange={handleCheckboxChange}
          style={{
            width: '20px',
            height: '20px',
            cursor: 'pointer',
            marginRight: '10px',
            borderRadius: '3px',
            // Default browser checkbox styling is used for simplicity
          }}
        />
        <label
          htmlFor="visible-checkbox"
          style={{
            fontSize: '14px',
            fontWeight: '400',
            color: '#333',
            userSelect: 'none',
          }}
        >
          I'm not a robot
        </label>
      </div>

      <input
        type="hidden"
        id="hidden-field"
        name="really-simple-captcha-response"
        value={isChecked ? 'true' : 'false'}
      />

      {/* Optional success text using inline styling */}
      {isChecked && (
        <div
          style={{
            fontSize: '10px',
            color: '#777',
            textAlign: 'right',
            paddingRight: '8px',
            paddingBottom: '4px',
          }}
        >
          Verification successful!
        </div>
      )}
    </div>
  );
}

class CustomElement extends HTMLElement {
  connectedCallback() {
    // Render the React <App /> component inside this custom element
    ReactDOM.render(
      React.createElement(App),
      this
    );
  }

  disconnectedCallback() {
    // Unmount the React component when the element is removed
    ReactDOM.unmountComponentAtNode(this);
  }
}

const ELEMENT_ID = 'liferay-devcon-really-simple-captcha-custom-element';

if (!customElements.get(ELEMENT_ID)) {
    customElements.define(ELEMENT_ID, CustomElement);
}