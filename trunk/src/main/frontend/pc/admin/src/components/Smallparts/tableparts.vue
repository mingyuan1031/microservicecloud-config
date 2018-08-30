<template>
	<i-switch v-model="formItem.switch" size="large">
                <span slot="open">{{yes}}</span>
                <span slot="close">{{no}}</span>
    </i-switch>
</template>

<script>
	export default {
		props: [
		'yes','no','ifshow'
		],
        data () {
            return {
                formItem: {             
                    switch: true
                }         
            }
        }
    }
</script>

<style>
</style>