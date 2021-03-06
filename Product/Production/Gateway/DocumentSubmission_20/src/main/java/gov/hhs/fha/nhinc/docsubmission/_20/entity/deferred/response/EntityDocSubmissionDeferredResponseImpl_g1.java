/*
 * Copyright (c) 2009-2018, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.docsubmission._20.entity.deferred.response;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.common.nhinccommonentity.RespondingGatewayProvideAndRegisterDocumentSetSecuredResponseRequestType;
import gov.hhs.fha.nhinc.docsubmission.DocSubmissionUtils;
import gov.hhs.fha.nhinc.docsubmission.outbound.deferred.response.OutboundDocSubmissionDeferredResponse;
import gov.hhs.fha.nhinc.event.error.ErrorEventException;
import gov.hhs.fha.nhinc.messaging.server.BaseService;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants.UDDI_SPEC_VERSION;
import gov.hhs.healthit.nhin.XDRAcknowledgementType;
import javax.xml.ws.WebServiceContext;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;


public class EntityDocSubmissionDeferredResponseImpl_g1 extends BaseService {

    private OutboundDocSubmissionDeferredResponse outboundDocSubmissionResponse;
    EntityDocSubmissionDeferredResponseImpl_g1(OutboundDocSubmissionDeferredResponse outboundDocSubmissionResponse) {
        this.outboundDocSubmissionResponse = outboundDocSubmissionResponse;
    }

    public XDRAcknowledgementType provideAndRegisterDocumentSetBResponse(
            RespondingGatewayProvideAndRegisterDocumentSetSecuredResponseRequestType provideAndRegisterDocumentSetSecuredResponseRequest,
            WebServiceContext context) {
        AssertionType assertion = getAssertion(context, null);
        return provideAndRegisterDocumentSetResponse(
                provideAndRegisterDocumentSetSecuredResponseRequest.getRegistryResponse(), assertion,
                provideAndRegisterDocumentSetSecuredResponseRequest.getNhinTargetCommunities());
    }

    public gov.hhs.healthit.nhin.XDRAcknowledgementType provideAndRegisterDocumentSetBAsyncResponse(
            gov.hhs.fha.nhinc.common.nhinccommonentity.RespondingGatewayProvideAndRegisterDocumentSetResponseRequestType provideAndRegisterDocumentSetAsyncRespRequest,
            WebServiceContext context) {
        AssertionType assertion = getAssertion(context, provideAndRegisterDocumentSetAsyncRespRequest.getAssertion());

        return provideAndRegisterDocumentSetResponse(
                provideAndRegisterDocumentSetAsyncRespRequest.getRegistryResponse(), assertion,
                provideAndRegisterDocumentSetAsyncRespRequest.getNhinTargetCommunities());
    }
    /**
     *
     * @param request
     * @param assertion
     * @param targets
     * @return
     */
    private XDRAcknowledgementType provideAndRegisterDocumentSetResponse(RegistryResponseType request,
            AssertionType assertion, NhinTargetCommunitiesType targets){
        XDRAcknowledgementType response = null;
        try{
            DocSubmissionUtils.getInstance().setTargetCommunitiesVersion(targets, UDDI_SPEC_VERSION.SPEC_2_0);
            response = outboundDocSubmissionResponse.provideAndRegisterDocumentSetBAsyncResponse(request,assertion,targets);
        }catch(Exception e){
            throw new ErrorEventException(e,"Unable to call Nhin Doc Submission");
        }
        return response;
    }
}
