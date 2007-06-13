<xsl:stylesheet 
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
   xmlns:fo="http://www.w3.org/1999/XSL/Format">

<xsl:output method="xml" indent="yes" encoding="US-ASCII"/>
<xsl:decimal-format decimal-separator="." grouping-separator="," />

<xsl:variable name="totalDistance">
     <xsl:value-of select="0"/>
</xsl:variable> 

<xsl:template match="/">
<fo:root>
   <fo:layout-master-set>
      <fo:simple-page-master master-name="A4"
           page-width="210mm"  page-height="297mm"
           margin-top="0.5in"  margin-bottom="0.5in"
           margin-left="0.5in" margin-right="0.5in">
          <fo:region-body margin="2cm"/>
          <fo:region-after extent="2cm"/>
      </fo:simple-page-master>
   </fo:layout-master-set>

   <xsl:apply-templates select="pdf"/>

</fo:root>
</xsl:template>

<xsl:template match="pdf">
   <fo:page-sequence master-reference="A4">
      <fo:static-content flow-name="xsl-region-after">
         <fo:block margin-left="2cm" text-align="end">
             Page: <fo:page-number/>/<fo:page-number-citation ref-id="end-of-document"/>
         </fo:block>
      </fo:static-content>
      <fo:flow flow-name="xsl-region-body">
         <fo:block font-size="14pt">
             <fo:inline>KTTGS Road Book</fo:inline>
         </fo:block>
         <fo:block font-size="14pt">
             <fo:leader leader-length="1in" leader-pattern="rule"/>
         </fo:block>
         <fo:block>Tour-ID: <xsl:value-of select="overview/data[@name='id']/text()"/></fo:block>
         <fo:block>Name:  <xsl:value-of select="overview/data[@name='name']/text()"/></fo:block>
         <fo:block>Length:  <xsl:value-of select="overview/data[@name='distance']/text()"/></fo:block>
         <fo:block>Duration: <xsl:value-of select="overview/data[@name='duration']/text()"/></fo:block>
         <fo:block>Uphill:  <xsl:value-of select="overview/data[@name='total-uphill']/text()"/></fo:block>
         
         <xsl:apply-templates select="overview/desc/xml-fragment"/>
         
      </fo:flow>
   </fo:page-sequence>

   <fo:page-sequence master-reference="A4">
      <fo:static-content flow-name="xsl-region-after">
         <fo:block margin-left="2cm">Page: <fo:page-number/>/<fo:page-number-citation ref-id="end-of-document"/></fo:block>
      </fo:static-content>
      <fo:flow flow-name="xsl-region-body">
         <fo:block font-size="14pt">
             <fo:inline>KTTGS Road Book</fo:inline>
         </fo:block>
         <fo:block font-size="14pt">
             <fo:leader leader-length="1in" leader-pattern="rule"/>
         </fo:block>
         
         <xsl:apply-templates select="crosspoints"/>
         
      </fo:flow>
   </fo:page-sequence>


   <fo:page-sequence master-reference="A4">
      <fo:static-content flow-name="xsl-region-after">
         <fo:block margin-left="2cm">Page: <fo:page-number/>/<fo:page-number-citation ref-id="end-of-document"/></fo:block>
      </fo:static-content>
      <fo:flow flow-name="xsl-region-body">
         <fo:block font-size="14pt">
             <fo:inline>KTTGS Road Book</fo:inline>
         </fo:block>
         <fo:block font-size="14pt">
             <fo:leader leader-length="1in" leader-pattern="rule"/>
         </fo:block>
         
          <xsl:apply-templates select="descs/info"/>
          
         <fo:block id="end-of-document"></fo:block>
      </fo:flow>
   </fo:page-sequence>

</xsl:template>

<xsl:template match="overview/desc/xml-fragment">
   <fo:block padding-before="5mm">Description</fo:block>
   <xsl:apply-templates select="para"/>
</xsl:template>

<xsl:template match="overview/desc/xml-fragment/para">
   <fo:block padding-before="2mm"><xsl:value-of select="text()"/></fo:block>
</xsl:template>


<xsl:template match="crosspoints">
   <fo:table>
      <fo:table-column column-width="2cm"/>
      <fo:table-column column-width="2cm"/>
      <fo:table-column column-width="2cm"/>
      <fo:table-column column-width="2cm"/>
      <fo:table-column column-width="3cm"/>
      <fo:table-column column-width="2cm"/>
      <fo:table-header>
         <fo:table-row> 
            <fo:table-cell><fo:block>Ges.(m)</fo:block></fo:table-cell>
            <fo:table-cell><fo:block>Entf.(m)</fo:block></fo:table-cell>
            <fo:table-cell><fo:block>Hoehe (m)</fo:block></fo:table-cell> 
            <fo:table-cell><fo:block>Richtung</fo:block></fo:table-cell> 
            <fo:table-cell><fo:block>Strassenbelag</fo:block></fo:table-cell> 
            <fo:table-cell><fo:block>Marks</fo:block></fo:table-cell> 
         </fo:table-row>
      </fo:table-header>
      <fo:table-body>
          <xsl:apply-templates select="crosspoint"/>
      </fo:table-body>
   </fo:table>
</xsl:template>

<xsl:template match="crosspoint">
   <fo:table-row>
      <fo:table-cell><fo:block><xsl:value-of select="cpdata[@name='total_dist']/text()"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="cpdata[@name='dist']/text()"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="cpdata[@name='elevation']/text()"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="cpdata[@name='direction']/text()"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="cpdata[@name='pavement']/text()"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="cpdata[@name='marks']/text()"/></fo:block></fo:table-cell>
   </fo:table-row>
</xsl:template>

<xsl:template match="descs/info">
   <fo:block padding-before="2mm">[<xsl:value-of select="@num"/>]</fo:block>
   
   <xsl:apply-templates select="xml-fragment/para|para"/>
   
</xsl:template>

<xsl:template match="xml-fragment/para|para">
   <fo:block><xsl:value-of select="text()"/></fo:block>
</xsl:template>

<xsl:template match="crosspoint/desc">
   <xsl:apply-templates select="para"/>
</xsl:template>

<xsl:template match="crosspoint/desc/para">
   <p><xsl:value-of select="text()"/></p>
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
      <!-- <xsl:attribute name="class">left_img</xsl:attribute> -->
      <xsl:attribute name="src">images/<xsl:value-of select = "@name"/>
      </xsl:attribute>
   </xsl:element>
   </p>
</xsl:template>

<xsl:template match="track-info">
   <!--
   <p class="crosspoint-info">
      <b>Streckenbelag</b>:
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
   -->
   <fo:table-cell><fo:block><xsl:value-of select="@pavement"/></fo:block></fo:table-cell>
</xsl:template>

<xsl:template match="eatndrink">
    <p class="crosspoint-info">Entfernung:<b><xsl:value-of select="@distance"/></b> (Lokal/Restaurant)</p>
    <xsl:if test="@image != ''">
       <p>
       <xsl:element name = "img" >
         <xsl:attribute name="class">left_img</xsl:attribute>
         <xsl:attribute name="src">images/<xsl:value-of select = "@image"/>
         </xsl:attribute>
      </xsl:element>
      </p>
    </xsl:if>
   <p><xsl:value-of select="text()"/></p>
   <p style="clear:left"/>
</xsl:template>

<xsl:template match="anecdote">
    <p class="crosspoint-info">Entfernung:<b><xsl:value-of select="@distance"/></b> (Anekdote)</p>
    <xsl:if test="@image != ''">
       <p>
       <xsl:element name = "img" >
         <xsl:attribute name="class">left_img</xsl:attribute>
         <xsl:attribute name="src">images/<xsl:value-of select = "@image"/>
         </xsl:attribute>
      </xsl:element>
      </p>
   </xsl:if>
   <p><xsl:value-of select="text()"/></p>
   <p style="clear:left"/>
</xsl:template>

<xsl:template match="poi">
    <p class="crosspoint-info">Entfernung:<b><xsl:value-of select="@distance"/></b> (Interessanter Punkt)</p>
    <xsl:if test="@image != ''">
       <p>
       <xsl:element name = "img" >
         <xsl:attribute name="class">left_img</xsl:attribute>
         <xsl:attribute name="src">images/<xsl:value-of select = "@image"/>
         </xsl:attribute>
      </xsl:element>
      </p>
   </xsl:if>
   <p><xsl:value-of select="text()"/></p>
   <p style="clear:left"/>
</xsl:template>

<xsl:template name="legend">
   <a name="legend"/>
   <h1>Legende</h1>
      <h2>Kondition</h2>
         <p class="crosspoint-info">
            <img src="images_intern/bar1.gif"/>Einfach
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/bar2.gif"/>Mittel
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/bar3.gif"/>Hoch
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/bar4.gif"/>Extrem
	 </p>
      <h2>Technisches Koennen</h2>
         <p class="crosspoint-info">
            <img src="images_intern/bar1.gif"/>Einfach
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/bar2.gif"/>Mittel
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/bar3.gif"/>Hoch
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/bar4.gif"/>Extrem
	 </p>
      <h2>Typ</h2>
         <p class="crosspoint-info">
            <img src="images_intern/tourab.gif"/>Tour von A nach B
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/tourr.gif"/>Rundtour
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/dh.gif"/>Reiner Downhill
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/trail.gif"/>Single Trail
	 </p>
      <h2>Streckenbelag</h2>
         <p class="crosspoint-info">
            <img src="images_intern/pavement-road.png"/>Teer/Strasse
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/pavement-forrest-road.png"/>Schotter-/Forstweg
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/pavement-trail.png"/>Trail/Wanderweg
	 </p>
      <h2>Weiter in Richtung</h2>
         <p class="crosspoint-info">
            <img src="images_intern/windrose-north.png"/>Norden
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/windrose-northeast.png"/>Nordost
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/windrose-east.png"/>Osten
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/windrose-southeast.png"/>Suedost
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/windrose-south.png"/>Sueden
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/windrose-southwest.png"/>Suedwest
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/windrose-west.png"/>Westen
	 </p>
         <p class="crosspoint-info">
            <img src="images_intern/windrose-northwest.png"/>Nordwest
	 </p>
</xsl:template>

</xsl:stylesheet>
