const configFile = require("../config/config.json");

const serverEnv = process.env.NODE_ENV;
const config = configFile[serverEnv];

export const BACKEND_URL = config["http://130.162.159.231:8080"];
export const HTTP_ONLY = config["HTTP_ONLY"];
