<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <?xml-stylesheet type="text/xsl" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"?>
    <xsl:template match="/">
        <html>
            <head>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
            </head>
            <body>
                <h2>My CD Collection</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Title</th>
                        <th>Artist</th>
                         <th>Icon</th>
                    </tr>
                    <xsl:for-each select="catalog/cd">
                            <tr>
                                <td><xsl:value-of select="title" /></td>
                                <td><xsl:value-of select="artist"/></td>
                                <td><xsl:choose>
                                    <xsl:when test="price &lt; 9">
                                        <i style="color:blue" class="fas fa-bone" ></i>
                                    </xsl:when>
                                    <xsl:when test="year &gt; 1995">
                                        <i style="color:red" class="fas fa-fire"></i>
                                    </xsl:when>
                                </xsl:choose></td>
                            </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>