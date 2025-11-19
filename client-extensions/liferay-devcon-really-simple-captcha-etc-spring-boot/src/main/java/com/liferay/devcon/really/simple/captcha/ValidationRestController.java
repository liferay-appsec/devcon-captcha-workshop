/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.devcon.really.simple.captcha;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;

import org.json.JSONObject;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/validation")
@RestController
public class ValidationRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject responseJSONObject = new JSONObject();

		System.out.println("JSON: " + json);

		JSONObject jsonObject = new JSONObject(json);

		if (Objects.equals(
				jsonObject.getString("response"), "true" )) {

			responseJSONObject.put(
					"success", Boolean.TRUE.toString());

			return new ResponseEntity<>(
					responseJSONObject.toString(), HttpStatus.OK);
		}

		responseJSONObject.put(
				"error-codes", "You are not a human!!" );
		return new ResponseEntity<>(
				responseJSONObject.toString(), HttpStatus.BAD_REQUEST);
	}

}