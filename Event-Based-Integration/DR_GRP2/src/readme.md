<p><strong>The folder Java contains one project and three services:</strong></p>
<p><strong>Project: StartFuseki</strong>:</p>
<p style="padding-left: 30px;">This folder contains the code which is necessary that the ontology will be uploaded on the Fuseki SPARQL server, so that queries can be executed on the ontology.</p>
<p style="padding-left: 30px;">&nbsp;</p>
<p><strong>Services:</strong></p>
<ul>
<li style="text-align: justify;"><strong>docProposalService</strong><br />This service contains the DocumentProposalAgent and the two&nbsp; InterestProfiles DocumentProposalIP and ProtocolProposalIP. The task of the DocumentProposalAgent is to find documents or protocol depending on the incoming tokens, which then appear in the User Interface.</li>
</ul>
<p>&nbsp;</p>
<ul>
<li style="text-align: justify;"><strong>saveProposalService</strong><br />This service contains the SaveDocumentAgent and the SaveDocument Interst Profile. The task of the SaveDocumentAgent has the task to save a new protocol after a video conference session.</li>
</ul>
<p>&nbsp;</p>
<ul>
<li style="text-align: justify;"><strong>semanticService</strong><br />This service contains the SemanticAgent and the SemanticChunk Interest Profile. The SemanticAgent has the task to find semantic information from the ontology for the incoming chunks.</li>
</ul>
<p>&nbsp;</p>
<p style="text-align: justify;">The table below gives an overview over all agents with the according topics and events, depending if these are subscribed or published by another group.</p>
<table>
<tbody>
<tr>
<td><strong>Agents/InterestProfiles</strong></td>
<td><strong>Topic (subscribed)</strong></td>
<td><strong>Event (subscribed)</strong></td>
<td><strong>Events (published)</strong></td>
<td><strong>Topic (published)</strong></td>
</tr>
<tr>
<td>
<p>SemanticAgent/</p>
<p>SemanticAgentInterestProfile</p>
</td>
<td>ChunkGeneration</td>
<td>SentenceEvent</td>
<td>FeedbackEvent</td>
<td>SemanticChunk</td>
</tr>
<tr>
<td>
<p>DocProposalAgent/</p>
<p>DocProposalInterestProfile</p>
</td>
<td>DocRequest</td>
<td>DocRequestEvent</td>
<td>DocProposalEvent</td>
<td>DocProposal</td>
</tr>
<tr>
<td>
<p>DocProposalAgent/</p>
<p>ProProposalInterestProfile</p>
</td>
<td>DocRequest</td>
<td>DocRequestEvent</td>
<td>DocProposalEvent</td>
<td>DocProposal</td>
</tr>
<tr>
<td>
<p>SaveDocumentAgent/</p>
<p>SaveDocumentInterestProfile</p>
</td>
<td>Protocol</td>
<td>ProtocolEvent</td>
<td>-</td>
<td>-</td>
</tr>
</tbody>
</table>
<p>&nbsp;</p>
