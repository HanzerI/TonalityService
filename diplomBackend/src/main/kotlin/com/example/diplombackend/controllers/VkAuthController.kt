package com.example.diplombackend.controllers

import com.example.diplombackend.entitys.Token
import com.example.diplombackend.services.UserService
import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.example.diplombackend.sotial.SocialNetwork
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView
import java.net.URLEncoder

@Controller
@RequestMapping("/auth")
@PreAuthorize("hasRole('USER')")
class VkAuthController(val userService: UserService) {

    @GetMapping("/vk")
    fun vkAuthRedirect(): RedirectView {
        val clientId = 	51733161
        val redirectUri = "http://188.93.24.21:8081/auth/vk/callback" // Укажите ваш Callback URI
        val vkAuthUrl = "https://oauth.vk.com/authorize?" +
                "client_id=$clientId" +
                "&redirect_uri=$redirectUri" +
                "&response_type=code" +
                "&scope=offline" // Укажите необходимые права доступа
        return RedirectView(vkAuthUrl)
    }

    @GetMapping("/vk/callback")
    fun vkAuthCallback(@RequestParam("code") code: String): String {
        val clientId = 	51733161
        val clientSecret = "5I0bkTyEP8CgN0kF3FfL"
        val redirectUri = "http://188.93.24.21:8081/auth/vk/callback" // Укажите ваш Callback URI
        val transportClient: TransportClient = HttpTransportClient()
        val vk = VkApiClient(transportClient)
        val authentication = SecurityContextHolder.getContext().authentication
        val authResponse = vk.oAuth()
            .userAuthorizationCodeFlow(clientId, clientSecret, redirectUri, code)
            .execute()

        userService.saveToken(authentication.name, Token(userTokenId = authResponse.userId.toLong(),
            token =  authResponse.accessToken,
            socialNetwork =  SocialNetwork.VKONTAKTE)
        )

        val userActor = UserActor(authResponse.userId, authResponse.accessToken)

        val user = vk.users().get(userActor).execute()[0]
        val fullName = "${user.firstName} ${user.lastName}"
        val encodedFullName = URLEncoder.encode(fullName, "UTF-8")
        return "redirect:/profile?name=$encodedFullName"
    }
}


