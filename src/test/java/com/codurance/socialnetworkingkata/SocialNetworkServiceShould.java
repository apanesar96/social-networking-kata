package com.codurance.socialnetworkingkata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SocialNetworkServiceShould {

	@Mock
	private Console console;

	@Test
	void submit_command() {
		SocialNetworkService socialNetworkService = new SocialNetworkService(console);
		given(console.readInput()).willReturn("input");

		socialNetworkService.submit();

		verify(console).output("input");
	}
}
