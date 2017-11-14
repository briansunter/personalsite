var assert = require('assert');
var rewritePath = require('../index.js').rewritePath

describe('rewritePath', function () {
    let realPage = "/personal-site/index.html"
    let siteSlash = "/personal-site/"

    it('replaces a path ending with a /', function () {
        assert.equal(rewritePath(siteSlash),realPage)
    });

    let siteNoSlash = "/personal-site"

    it('replaces a path NOT ending with a /', function () {
        assert.equal(rewritePath(siteNoSlash),realPage)
    });

    it('should not mess with extensions', function () {
        assert.equal(rewritePath(realPage),realPage)
    });

    let root = "https://bsun.io/"
    let realRoot = "https://bsun.io/index.html"

    it('root should work', function () {
        assert.equal(rewritePath(root),realRoot)
    });

});
