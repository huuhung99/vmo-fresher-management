package devil.common.enums;

public enum ResponseCodeEnum implements ResponseCode {
    /**
     * {@code 100 Continue}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.2.1">HTTP/1.1:
     *      Semantics and Content, section 6.2.1</a>
     */
    R_100,

    /**
     * {@code 101 Switching Protocols}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.2.2">HTTP/1.1:
     *      Semantics and Content, section 6.2.2</a>
     */
    R_101,

    /**
     * {@code 102 Processing}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc2518#section-10.1">WebDAV</a>
     */
    R_102,

    /**
     * {@code 103 Checkpoint}.
     *
     * @see <a href=
     *      "http://code.google.com/p/gears/wiki/ResumableHttpRequestsProposal">
     *      A proposal for supporting
     *      resumable POST/PUT HTTP requests in HTTP/1.0</a>
     */
    R_103,
    /**
     * {@code 200 OK}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.3.1">HTTP/1.1:
     *      Semantics and Content, section 6.3.1</a>
     */
    R_200,
    
    R_201,
    /**
     * {@code 304 Not Modified}.
     * @see <a href="http://tools.ietf.org/html/rfc7232#section-4.1">HTTP/1.1: Conditional Requests, section 4.1</a>
     */
    R_304,
    
    /**
     * {@code 400 Bad Request}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.1">HTTP/1.1:
     *      Semantics and Content, section 6.5.1</a>
     */
    R_400,

    /**
     * {@code 401 Unauthorized}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.1">HTTP/1.1:
     *      Authentication, section 3.1</a>
     */
    R_401,

    /**
     * {@code 402 Payment Required}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.2">HTTP/1.1:
     *      Semantics and Content, section 6.5.2</a>
     */
    R_402,

    /**
     * {@code 403 Forbidden}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.3">HTTP/1.1:
     *      Semantics and Content, section 6.5.3</a>
     */
    R_403,

    /**
     * {@code 404 Not Found}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.4">HTTP/1.1:
     *      Semantics and Content, section 6.5.4</a>
     */
    R_404,

    /**
     * {@code 405 Method Not Allowed}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.5">HTTP/1.1:
     *      Semantics and Content, section 6.5.5</a>
     */
    R_405,
     /**
     * {@code 500 Internal Server Error}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.1">HTTP/1.1:
     *      Semantics and Content, section 6.6.1</a>
     */
    R_500,
    
    E_1000,	// Invalid JWT token
    E_1001, // Expired JWT token
    E_1002, // Unsupported JWT token
    E_1003, // JWT claims string is empty.
}
