(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderStatusController', CandidateJoborderStatusController);

    CandidateJoborderStatusController.$inject = ['$scope', '$state', 'CandidateJoborderStatus'];

    function CandidateJoborderStatusController ($scope, $state, CandidateJoborderStatus) {
        var vm = this;

        vm.candidateJoborderStatuses = [];

        loadAll();

        function loadAll() {
            CandidateJoborderStatus.query(function(result) {
                vm.candidateJoborderStatuses = result;
            });
        }
    }
})();
