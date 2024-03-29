// NOTE: To use this example standalone (e.g. outside of deck.gl repo)
// delete the local development overrides at the bottom of this file

// avoid destructuring for older Node version support
const resolve = require('path').resolve;
const webpack = require('webpack');

module.exports = {
    entry: {
        app: resolve('./app.js')
    },

    devtool: 'source-map',

    module: {
        rules: [{
            // Compile ES2015 using buble
            test: /\.js$/,
            loader: 'buble-loader',
            include: [resolve('.')],
            exclude: [/node_modules/],
            options: {
                objectAssign: 'Object.assign'
            }
        }]
    },

    output: {
        path: __dirname,
        publicPath: '/',
        filename: 'bundle.js'
    },

    resolve: {
        alias: {
            // From mapbox-gl-js README. Required for non-browserify bundlers (e.g. webpack):
            'mapbox-gl$': resolve('./node_modules/mapbox-gl/dist/mapbox-gl.js')
        }
    }
};

// DELETE THIS LINE WHEN COPYING THIS EXAMPLE FOLDER OUTSIDE OF DECK.GL
// It enables bundling against src in this repo rather than installed deck.gl module
//module.exports = require('../webpack.config.local')(module.exports);
