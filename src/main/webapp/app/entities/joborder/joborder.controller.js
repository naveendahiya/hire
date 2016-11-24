(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('JoborderController', JoborderController);

    JoborderController.$inject = ['$scope', '$state', 'Joborder'];

    function JoborderController ($scope, $state, Joborder) {
        var vm = this;

        vm.joborders = [];

        loadAll();

        function loadAll() {
            Joborder.query(function(result) {
                vm.joborders = result;
            });
        }
    }
})();
