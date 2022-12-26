export default async function handler(req, res) {
  console.log(req);
  await axios
    .post("http://193.123.230.252:8080/api/login", req)
    .then(({ data }) => {
      res.status(200).json({ data });
    });
}
