/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.credentialStore

import com.intellij.util.nullize

data class CredentialAttributes(val serviceName: String, val accountName: String? = null) {
}

// user cannot be empty, but password can be
class Credentials(user: String?, val password: String?) {
  val user = user.nullize()

  override fun equals(other: Any?): Boolean {
    if (other !is Credentials) return false
    return user == other.user && password == other.password
  }

  override fun hashCode() = (user?.hashCode() ?: 0) * 37 + (password?.hashCode() ?: 0)
}

interface CredentialStore {
  fun get(attributes: CredentialAttributes): Credentials?

  fun getPassword(attributes: CredentialAttributes) = get(attributes)?.password

  fun set(attributes: CredentialAttributes, credentials: Credentials?)

  fun setPassword(attributes: CredentialAttributes, password: String?) {
    set(attributes, Credentials(attributes.accountName, password))
  }
}
