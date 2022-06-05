import axios from 'axios'
import { hostIp } from '../config/config.js'

export const registerUserApi = async (userInfo) => {
  try {
    const res = await axios.post(`${hostIp}/account/register`, {
      username: userInfo.username,
      email: userInfo.email,
      password: userInfo.password
    }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}
export const loginUserApi = async (userInfo) => {
  try {
    const res = await axios.post(`${hostIp}/account/login`, {
      username: userInfo.username,
      password: userInfo.password
    }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}
