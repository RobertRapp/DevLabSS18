'use strict';

module.exports = {
  entry: './public/webpack-app.js',
  output: {
    path: __dirname + '/static',
    filename: 'webpack-bundle.js'
  }
};
