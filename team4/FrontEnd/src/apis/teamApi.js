import axios from 'axios'
import { hostIp } from '../config/config.js'

export const createTeamApi = async (teamInfo) => {
  try {
    const res = await axios.post(`${hostIp}/team/createTeam`, {
      username: teamInfo.username,
      teamName: teamInfo.teamName
    }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const getBoardIdInTeamApi = async (username) => {
  try {
    const res = await axios.post(`${hostIp}/user/${username}/getBoardList`, {
    }
    )

    return JSON.parse(res.data.message)
  } catch (err) {
    console.log(err)
  }
}
