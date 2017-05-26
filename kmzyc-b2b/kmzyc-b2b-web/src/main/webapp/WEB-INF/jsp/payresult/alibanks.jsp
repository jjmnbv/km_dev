<%@ page language="java" pageEncoding="UTF-8"%>
<style>
	.cashier-bank label{cursor: pointer;}
</style>
<div class="bank-list fn-clear">
   	<div class="long-logo"> 
    	<ul class="ui-list-icons ui-four-icons fn-clear cashier-bank" id="ali_bank_list" style="width: 960px;"> 
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_abc" title="中国农业银行" value="ABC" />
		    	<label class="ABC" for="ali_bank_abc" title="中国农业银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_icbc" title="中国工商银行" value="ICBCB2B" />
		    	<label class="ICBC" for="ali_bank_icbc" title="中国工商银行"></label>
			</li> 
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_ccb" title="中国建设银行" value="CCB" />
		    	<label class="CCB" for="ali_bank_ccb" title="中国建设银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_psbc" title="中国邮政储蓄银行" value="POSTGC" />
		    	<label class="PSBC" for="ali_bank_psbc" title="中国邮政储蓄银行"></label>
		    </li> 
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_boc" title="中国银行" value="BOCB2B" />
		    	<label class="BOC" for="ali_bank_boc"title="中国银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_cmb" title="招商银行" value="CMB" />
		    	<label class="CMB" for="ali_bank_cmb" title="招商银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_comm" title="交通银行" value="COMM" />
		    	<label class="COMM" for="ali_bank_comm" title="交通银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_spdb" title="浦发银行" value="SPDB" />
		    	<label class="SPDB" for="ali_bank_spdb" title="浦发银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_ceb" title="中国光大银行" value="CEB-DEBIT" />
		    	<label class="CEB" for="ali_bank_ceb" title="中国光大银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_citic" title="中信银行" value="CITIC-DEBIT" />
		    	<label class="CITIC" for="ali_bank_citic" title="中信银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_spabank" title="平安银行" value="SPABANK" />
		    	<label class="SPABANK" for="ali_bank_spabank" title="平安银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_cmbc" title="中国民生银行" value="CMBC" />
		    	<label class="CMBC" for="ali_bank_cmbc" title="中国民生银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_gdb" title="广发银行" value="GDB" />
		    	<label class="GDB" for="ali_bank_gdb" title="广发银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_cib" title="兴业银行" value="CIB" />
		    	<label class="CIB" for="ali_bank_cib" title="兴业银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_shbank" title="上海银行" value="SHBANK" />
		    	<label class="SHBANK" for="ali_bank_shbank" title="上海银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_nbbank" title="宁波银行" value="NBBANK" />
		    	<label class="NBBANK" for="ali_bank_nbbank" title="宁波银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_hzcb" title="杭州银行" value="HZCBB2B" />
		    	<label class="HZCB" for="ali_bank_hzcb" title="杭州银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_bjbank" title="北京银行" value="BJBANK" />
		    	<label class="BJBANK" for="ali_bank_bjbank" title="北京银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_bjrcb" title="北京农村商业银行" value="BJRCB" />
		    	<label class="BJRCB" for="ali_bank_bjrcb" title="北京农村商业银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_wzcbb2b" title="温州银行" value="WZCBB2B-DEBIT" />
		    	<label class="WZCBB2B" for="ali_bank_wzcbb2b" title="温州银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_shrcb" title="上海农商银行" value="SHRCB" />
		    	<label class="SHRCB" for="ali_bank_shrcb" title="上海农商银行"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_fdb" title="富滇银行" value="FDB" />
		    	<label class="FDB" for="ali_bank_fdb" title="富滇银行"></label>
		    </li>
		    <!-- 
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_abc1003" title="visa" value="abc1003" />
		    	<label class="abc1003" for="ali_bank_abc1003" title="visa"></label>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_abc1004" title="master" value="abc1004" />
		    	<label class="abc1004" for="ali_bank_abc1004" title="master"></label>
		    </li>
		     -->
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_icbcb2b" title="中国工商银行"  value="ICBCBTB" />
		    	<label class="ICBC" for="ali_bank_icbcb2b" title="中国工商银行" ></label>
		    	<span>企业</span>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_ccbb2b" title="中国建设银行"  value="CCBBTB" />
		    	<label class="CCB" for="ali_bank_ccbb2b" title="中国建设银行" ></label>
		    	<span>企业</span>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_abcb2b" title="中国农业银行"  value="ABCBTB" />
		    	<label class="ABC" for="ali_bank_abcb2b" title="中国农业银行" ></label>
		    	<span>企业</span>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_cmbb2b" title="招商银行" value="CMBBTB" />
		    	<label class="CMB" for="ali_bank_cmbb2b" title="招商银行" ></label>
		    	<span>企业</span>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_bocb2b" title="中国银行" value="BOCBTB" />
		    	<label class="BOC" for="ali_bank_bocb2b" title="中国银行" ></label>
		    	<span>企业</span>
		    </li>
		    <li>
		    	<input type="radio" name="bank_type" id="ali_bank_spdbb2b" title="上海浦东发展银行" value="SPDBB2B" />
		    	<label class="SPDB" for="ali_bank_spdbb2b" title="上海浦东发展银行" ></label>
		    	<span>企业</span>
		    </li>
		</ul>
	</div> 
</div>