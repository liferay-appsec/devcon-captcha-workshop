/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.altcha;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.altcha.altcha.Altcha;
import org.altcha.altcha.Altcha.Algorithm;
import org.altcha.altcha.Altcha.Challenge;
import org.altcha.altcha.Altcha.ChallengeOptions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Manuele Castro
 * @author Pedro Victor Silvestre
 */
@RequestMapping("/validation")
@RestController
public class ValidationRestController extends BaseRestController {

	@Value("${liferay.altcha.secret:secret-key}")
	private String hmacKey;

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject responseJSONObject = new JSONObject();

		JSONObject jsonObject = new JSONObject(json);

		try {
			Altcha.verifySolution(jsonObject.getString("response"), hmacKey, true);

			responseJSONObject.put("success", Boolean.TRUE.toString());

			return new ResponseEntity<>(
					responseJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			responseJSONObject.put(
					"error-codes",
					"Error verifying solution: " + exception.getMessage());

			return  new ResponseEntity<>(
					responseJSONObject.toString(), HttpStatus.BAD_REQUEST);
		}

	}

}