(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EmailTemplateController', EmailTemplateController);

    EmailTemplateController.$inject = ['$scope', '$state', 'EmailTemplate'];

    function EmailTemplateController ($scope, $state, EmailTemplate) {
        var vm = this;

        vm.emailTemplates = [];

        loadAll();

        function loadAll() {
            EmailTemplate.query(function(result) {
                vm.emailTemplates = result;
            });
        }
    }
})();
