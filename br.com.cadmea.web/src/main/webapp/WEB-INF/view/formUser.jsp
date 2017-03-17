<div class="row">
	<div class="input-field">
		<i class="material-icons prefix">account_circle</i> 
		<input id="nickname" ng-model="user.entity.nickname" type="text" class="validate"> 
		<label for="nickname">Nickname</label>
	</div>
</div>

<div class="row">
	<div class="input-field">
		<i class="material-icons prefix">email</i> 
		<input id="email" ng-model="user.entity.email" type="email" class="validate"> 
		<label for="email" data-error="wrong" data-success="right">Email</label>
	</div>
</div>

<div class="row">
	<div class="input-field col s6">
		<i class="material-icons prefix">lock_open</i> 
		<input id="password" ng-model="user.entity.password" type="password"> 
		<label for="password">Password</label>
	</div>

	<div class="input-field col s6">
		<i class="material-icons prefix">lock_open</i> 
		<input id="repeatPassword" ng-model="user.entity.repeatPassword" type="password"> 
		<label for="repeatPassword">Confirm Password</label>
	</div>
</div>