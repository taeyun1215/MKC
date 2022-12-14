export default async function handler(req, res) {
    await axios
      .post("http://193.123.230.252:8080/api/user", {
        nickname: "버드실버",
        username: "ose0401",
        password: "pp930202!!",
        confirmPassword: "pp930202!!",
        email: "seo@goldenplanet.co.kr",
      })
      .then(({ data }) => {
        res.status(200).json({ data });
      });
  }