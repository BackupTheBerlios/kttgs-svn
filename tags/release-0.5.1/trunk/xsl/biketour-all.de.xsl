<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes" encoding="US-ASCII"/>
 
<xsl:decimal-format decimal-separator="." grouping-separator="," />

<xsl:template match="/">
<html>
  <head>
    <style type="text/css">
    </style>
    <title>Bike Tour XML</title>
    <link rel="stylesheet" type="text/css" href="biketour.css"/>
  </head>
  <body bgcolor="lightgreen">
    <xsl:apply-templates select="tour-guide"/>
  </body>
</html>
</xsl:template>

<xsl:template match="tour-guide">
   <h1>knottyTom's TourGuide System</h1>
   
   <!--
   <h1>
      [<a class="menu" href="#how.to.get.there">Anreise</a>]
      [<a class="menu" href="#useful.maps">Empfohlenes Kartenmaterial</a>]
      [<a class="menu" href="#overview.map">Uebersichtskarte</a>]
      [<a class="menu" href="#profile.map">Tour-Profil</a>]
      [<a class="menu" href="#crosspoints">Detaillierte Tourinformationen</a>]
   </h1>
   -->
   
   <center>
   <div id="navcontainer">
      <a href="#how.to.get.there">Anreise</a>
      <a href="#useful.maps">Empfohlenes Kartenmaterial</a>
      <a href="#overview.map">Uebersichtskarte</a>
      <a href="#profile.map">Tour-Profil</a>
      <a href="#crosspoints">Detaillierte Tourinformationen</a>
   </div>
   </center>
   <xsl:apply-templates select="general"/>
   <a name="crosspoints"/>
   <h1>Detaillierte Tourinformationen</h1>
   <xsl:apply-templates select="crosspoints"/>
</xsl:template>

<xsl:template match="general">
     <h2><xsl:value-of select="name"/></h2>
     <p><xsl:value-of select="desc"/></p>
     <table>
        <tr><td>Kondition</td><td>
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
        <tr><td>Technisches Koennen</td><td>
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
        <tr><td>Typ</td>
        <td>
           <!-- type: downhill (dh.gif), 
                      pointtopoint (tourab.gfi), 
                      round (tourr.gif), 
                      trail (trail.gif)
           -->
           <xsl:choose >
              <xsl:when test = "@type='downhill'" >
                   <img src="images_intern/dh.gif"/>
              </xsl:when>
              <xsl:when test = "@type='pointtopoint'" >
                   <img src="images_intern/tourab.gif"/>
              </xsl:when>
              <xsl:when test = "@type='round'" >
                   <img src="images_intern/tourr.gif"/>
              </xsl:when>
              <xsl:when test = "@type='trail'" >
                   <img src="images_intern/trail.gif"/>
              </xsl:when>
              <xsl:otherwise >
                   <xsl:text>[?UNKNOWN?]</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
        </td></tr>
        <tr><td>Dauer</td><td><xsl:value-of select="@duration"/></td></tr>
        <tr><td>Laenge</td><td><xsl:apply-templates select="distance"/></td></tr>
	<tr><td>Hoehenmeter</td><td><xsl:value-of select="@total-uphill"/></td></tr>
     </table>
     <xsl:apply-templates select="reach"/>
     <xsl:apply-templates select="maps"/>
     <xsl:apply-templates select="roadmap"/>
     <xsl:apply-templates select="profile"/>
</xsl:template>

<xsl:template match="distance">
   <xsl:value-of select="text()"/> <xsl:value-of select="@unit"/>
</xsl:template>

<xsl:template match="reach">
   <a name="how.to.get.there"/>
   <h2>Anreise</h2>
   <xsl:apply-templates select="option"/>
</xsl:template>

<xsl:template match="option">
    <h3><xsl:value-of select="@name"/></h3>
       <p><xsl:value-of select="text()"/></p>
</xsl:template>

<xsl:template match="maps">
   <a name="useful.maps"/>
   <h2>Empfohlenes Kartenmaterial</h2>
   <ul>
      <xsl:apply-templates select="map"/>
   </ul>
</xsl:template>

<xsl:template match="map">
    <li><xsl:value-of select="text()"/></li>
</xsl:template>

<xsl:template match="roadmap">
   <h2>Uebersichtskarte</h2>
   <a name="overview.map"/>
   <xsl:element name = "img" >
      <xsl:attribute name="src">images/<xsl:value-of select = "@image"/>
      </xsl:attribute>
   </xsl:element>
</xsl:template>

<xsl:template match="profile">
   <h2>Tour-Profil</h2>
   <xsl:element name = "img" >
      <xsl:attribute name="src">images_intern/profile.png</xsl:attribute>
   </xsl:element>
   <a name="profile.map"/>
   <ol>
      <xsl:apply-templates select="/tour-guide/crosspoints/crosspoint" mode="profile"/>
   </ol>
   
</xsl:template>

<xsl:template match="crosspoints">
   <xsl:apply-templates select="crosspoint|track-info"/>
</xsl:template>

<xsl:template match="crosspoint">
   <h2>Abzweigung</h2>
   <p class="crosspoint-info">
      Entfernung:<b><xsl:value-of select="@distance"/></b>
      Hoehe:<b><xsl:value-of select="@elevation"/></b>
      <!--  Weiter in Richtung: -->
   </p>
   <p class="crosspoint-info">
      Breitengrad:<b><xsl:value-of select="@latitude"/></b>
      Laengengrad:<b><xsl:value-of select="@longitude"/></b>
   </p>
   <p class="windrose">
      <xsl:element name = "img" >
         <xsl:choose >
           <xsl:when test = "@direction='north'" >
              <xsl:attribute name="src">images_intern/windrose-north.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@direction='east'" >
              <xsl:attribute name="src">images_intern/windrose-east.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@direction='south'" >
              <xsl:attribute name="src">images_intern/windrose-south.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@direction='west'" >
              <xsl:attribute name="src">images_intern/windrose-west.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@direction='northeast'" >
              <xsl:attribute name="src">images_intern/windrose-northeast.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@direction='southeast'" >
              <xsl:attribute name="src">images_intern/windrose-southeast.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@direction='southwest'" >
              <xsl:attribute name="src">images_intern/windrose-southwest.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@direction='northwest'" >
              <xsl:attribute name="src">images_intern/windrose-northwest.png</xsl:attribute>
           </xsl:when>
           <xsl:otherwise >
              <xsl:attribute name="src">images_intern/windrose-error.png</xsl:attribute>
           </xsl:otherwise>
         </xsl:choose>
      </xsl:element>
   </p>
   <p>
   <xsl:apply-templates select="desc"/>
   <xsl:apply-templates select="images/image"/>
   </p>
</xsl:template>

<xsl:template match="crosspoint/desc">
   <xsl:apply-templates select="text()"/>
</xsl:template>

<xsl:template match="/tour-guide/crosspoints/crosspoint" mode="profile">
   <xsl:if test="profile-desc">
       <xsl:apply-templates select="profile-desc" mode="profile"/>
   </xsl:if>
   <xsl:if test="not(profile-desc)">
       <xsl:apply-templates select="desc" mode="profile"/>
   </xsl:if>
</xsl:template>

<xsl:template match="/tour-guide/crosspoints/crosspoint/desc" mode="profile">
   <li><xsl:apply-templates select="text()"/></li>
</xsl:template>

<xsl:template match="/tour-guide/crosspoints/crosspoint/profile-desc" mode="profile">
   <li><xsl:apply-templates select="text()"/></li>
</xsl:template>


<xsl:template match="crosspoint/images/image">
   <p>
   <xsl:element name = "img" >
      <xsl:attribute name="src">images/<xsl:value-of select = "@name"/>
      </xsl:attribute>
   </xsl:element>
   </p>
</xsl:template>

<xsl:template match="track-info">
   <h3>Informationen zum Streckenabschnitt</h3>
   <p class="crosspoint-info">
      <b>Streckenbelag</b>: <!-- <xsl:value-of select="@pavement"/> -->
      <xsl:element name = "img" >
         <xsl:choose >
           <xsl:when test = "@pavement='road'" >
              <xsl:attribute name="src">images_intern/pavement-road.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@pavement='forrest.road'" >
              <xsl:attribute name="src">images_intern/pavement-forrest-road.png</xsl:attribute>
           </xsl:when>
           <xsl:when test = "@pavement='trail'" >
              <xsl:attribute name="src">images_intern/pavement-trail.png</xsl:attribute>
           </xsl:when>
           <xsl:otherwise >
              <xsl:attribute name="src">images_intern/pavement-base.png</xsl:attribute>
           </xsl:otherwise>
         </xsl:choose>
      </xsl:element>
   </p>
    <xsl:apply-templates select="eatndrink|anecdote|poi"/>
</xsl:template>

<xsl:template match="eatndrink">
    <p class="crosspoint-info">Entfernung:<b><xsl:value-of select="@distance"/></b> (Lokal/Restaurant)</p>
    <p><xsl:value-of select="text()"/></p>
    <xsl:if test="@image != ''">
       <p>
       <xsl:element name = "img" >
         <xsl:attribute name="src">images/<xsl:value-of select = "@image"/>
         </xsl:attribute>
      </xsl:element>
      </p>
    </xsl:if>
</xsl:template>

<xsl:template match="anecdote">
    <p class="crosspoint-info">Entfernung:<b><xsl:value-of select="@distance"/></b> (Anekdote)</p>
    <p><xsl:value-of select="text()"/></p>
    <xsl:if test="@image != ''">
       <p>
       <xsl:element name = "img" >
         <xsl:attribute name="src">images/<xsl:value-of select = "@image"/>
         </xsl:attribute>
      </xsl:element>
      </p>
   </xsl:if>
</xsl:template>

<xsl:template match="poi">
    <p class="crosspoint-info">Entfernung:<b><xsl:value-of select="@distance"/></b> (Interessanter Punkt)</p>
    <p><xsl:value-of select="text()"/></p>
    <xsl:if test="@image != ''">
       <p>
       <xsl:element name = "img" >
         <xsl:attribute name="src">images/<xsl:value-of select = "@image"/>
         </xsl:attribute>
      </xsl:element>
      </p>
   </xsl:if>
</xsl:template>

</xsl:stylesheet>
