const ProxyConf = [
  {
    context: ['/api'],
    target: 'http://localhost:8080/',
    secure: false,
    logLevel: 'debug',
  }
]
module.exports = ProxyConf
