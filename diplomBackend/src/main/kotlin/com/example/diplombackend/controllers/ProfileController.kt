package com.example.diplombackend.controllers
import com.example.diplombackend.entitys.User.Companion.getToken
import com.example.diplombackend.entitys.User.Companion.socialId
import com.example.diplombackend.services.UserService
import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.example.diplombackend.sotial.SocialNetwork

import java.security.Principal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.net.URLEncoder

@Controller
class ProfileController(private val userService: UserService) {

    @GetMapping("/profile")
    fun showProfilePage(model: Model, principal: Principal): String {
        val username = principal.name
        val user = userService.getUserByUsername(username)!!
        val userActor = UserActor(user.socialId(SocialNetwork.VKONTAKTE)?.toInt() ,user.getToken(SocialNetwork.VKONTAKTE))

        val transportClient: TransportClient = HttpTransportClient()
        val vk = VkApiClient(transportClient)
        val user2 = vk.users().get(userActor).execute()[0]
        val fullName = "${user2.firstName} ${user2.lastName}"
        val encodedFullName = URLEncoder.encode(fullName, "UTF-8")

        model["user"] = fullName
        return "profile"
    }

    @PostMapping("/profile/add-tokens")
    fun addTokens(@RequestParam("vkToken") vkToken: String?, @RequestParam("telegramToken") telegramToken: String?, principal: Principal): String {
        val username = principal.name
//        val user: UserDetails = userService.loadUserByUsername(username)
//
//        if (!vkToken.isNullOrEmpty()) {
//            user.socialTokens[com.example.diplombackend.sotial.SocialNetwork.VKONTAKTE] = vkToken
//        }
//        if (!telegramToken.isNullOrEmpty()) {
//            user.socialTokens[com.example.diplombackend.sotial.SocialNetwork.TELEGRAM] = telegramToken
//        }
//
//        userService.saveUser(user)
        return "redirect:/profile"
    }
}
