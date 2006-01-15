<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes" encoding="US-ASCII"/>
 
<xsl:decimal-format decimal-separator="." grouping-separator="," />

<xsl:template match="/">
<html>
  <head>
    <style type="text/css">
    </style>
    <title>Bike Tour XML</title>
  </head>
  <body>
    <xsl:apply-templates select="tour-guide"/>
  </body>
</html>
</xsl:template>

<xsl:template match="tour-guide">
   <hr/>
   <h1>knottyTom's TourGuide System</h1>
   <hr/>
   <hr/>
   Please see that this is the 0.1 version of the stylesheet missing all of
   the neat features (like having images). It is more a 'proof of concept' than
   a selling argument.
   <hr/>
   <hr/>   
      <xsl:apply-templates select="general"/>
   <hr/><h2>The Tour Info Starts Here</h2><hr/>
       <xsl:apply-templates select="crosspoints"/>
</xsl:template>

<xsl:template match="general">
     <h2><xsl:value-of select="name"/></h2>
     <p><xsl:value-of select="desc"/></p>
     <table>
        <tr><td>Fitness</td><td>
            <xsl:choose >
              <xsl:when test = "@fitness-level='easy'" >
                   <img src="images_intern/bar1.gif"/>
              </xsl:when>
              <xsl:when test = "@fitness-level='medium'" >
                   <img src="images_intern/bar2.gif"/>
              </xsl:when>
              <xsl:when test = "@fitness-level='high'" >
                   <img src="images_intern/bar3.gif"/>
              </xsl:when>
              <xsl:when test = "@fitness-level='extrem'" >
                   <img src="images_intern/bar4.gif"/>
              </xsl:when>
              <xsl:otherwise >
                   <xsl:text>[?UNKNOWN?]</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
        </td></tr>
        <tr><td>Tech</td><td>
            <xsl:choose >
              <xsl:when test = "@tech-level='easy'" >
                   <img src="images_intern/bar1.gif"/>
              </xsl:when>
              <xsl:when test = "@tech-level='medium'" >
                   <img src="images_intern/bar2.gif"/>
              </xsl:when>
              <xsl:when test = "@tech-level='high'" >
                   <img src="images_intern/bar3.gif"/>
              </xsl:when>
              <xsl:when test = "@tech-level='extrem'" >
                   <img src="images_intern/bar4.gif"/>
              </xsl:when>
              <xsl:otherwise >
                   <xsl:text>[?UNKNOWN?]</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
        </td></tr>
        <tr><td>Type</td><td><img src="images_intern/tourr.gif"/></td></tr>
        <tr><td>Duration</td><td><xsl:value-of select="@duration"/></td></tr>
        <tr><td>Distance</td><td><xsl:apply-templates select="distance"/></td></tr>
     </table>
     <xsl:apply-templates select="reach"/>
     <xsl:apply-templates select="maps"/>
</xsl:template>

<xsl:template match="distance">
   <xsl:value-of select="text()"/> <xsl:value-of select="@unit"/>
</xsl:template>

<xsl:template match="reach">
   <h2>How To Get There</h2>
   <ul>
      <xsl:apply-templates select="option"/>
   </ul>
</xsl:template>

<xsl:template match="option">
    <li>Via <b><xsl:value-of select="@name"/></b>:
       <xsl:value-of select="text()"/>
   </li>
</xsl:template>

<xsl:template match="maps">
   <h2>Useful Maps</h2>
   <ul>
      <xsl:apply-templates select="map"/>
   </ul>
</xsl:template>

<xsl:template match="map">
    <li><xsl:value-of select="text()"/></li>
</xsl:template>

<xsl:template match="crosspoints">
   <xsl:apply-templates select="crosspoint|track-info"/>
</xsl:template>

<xsl:template match="crosspoint">
   <p><b>Crosspoint</b>
   (dist:<b><xsl:value-of select="@distance"/></b>/
      elevation:<b><xsl:value-of select="@elevation"/></b>/
      direction to go:<b><xsl:value-of select="@direction"/></b>)
   <xsl:apply-templates select="desc"/>
   </p>
</xsl:template>

<xsl:template match="crosspoint/desc">
   <xsl:apply-templates select="text()"/>
</xsl:template>

<xsl:template match="track-info">
   <p>Track Info (<b>Pavement</b>: <xsl:value-of select="@pavement"/>)</p>
   <ul>
      <xsl:apply-templates select="eatndrink|anecdote|poi"/>
   </ul>
</xsl:template>

<xsl:template match="eatndrink">
    <li>Eatndrink at <b><xsl:value-of select="@distance"/></b>:<xsl:value-of select="text()"/>
       (Type: <xsl:value-of select="@type"/>).
    </li>
</xsl:template>

<xsl:template match="anecdote">
    <li>Anecdote at <b><xsl:value-of select="@distance"/></b>:<xsl:value-of select="text()"/></li>
</xsl:template>

<xsl:template match="poi">
    <li>Point-Of-Interest at <b><xsl:value-of select="@distance"/></b>:<xsl:value-of select="text()"/></li>
</xsl:template>

</xsl:stylesheet>
